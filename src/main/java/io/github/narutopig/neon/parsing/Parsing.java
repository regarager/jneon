package io.github.narutopig.neon.parsing;

import io.github.narutopig.neon.exec.functions.Functions;
import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.statement.statements.Declaration;
import io.github.narutopig.neon.exec.statement.statements.FunctionCall;
import io.github.narutopig.neon.exec.statement.statements.FunctionDeclaration;
import io.github.narutopig.neon.exec.statement.statements.Program;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.Value;
import io.github.narutopig.neon.parsing.token.Token;
import io.github.narutopig.neon.parsing.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parsing {
    private final List<Token> tokens;
    private final Map<String, NeonFunction> functions = Functions.getFunctions();
    private final Memory memory;

    public Parsing(List<Token> tokens, Memory memory) {
        this.tokens = tokens;
        this.memory = memory;
    }

    public static List<List<Token>> children(List<Token> toks) {
        List<List<Token>> stuffs = new ArrayList<>();

        List<Token> curr = new ArrayList<>();

        int depth = 0; // for curly braces
        int prevdepth = 0;
        for (Token token : toks) {
            curr.add(token);

            TokenType tt = token.getType();
            if (tt == TokenType.SEMI && depth == 0) {
                stuffs.add(new ArrayList<>(curr));
                curr.clear();
            } else if (tt == TokenType.LEFTCURLY) {
                prevdepth = depth;
                depth++;
            } else if (tt == TokenType.RIGHTCURLY) {
                prevdepth = depth;
                depth--;
            }

            if (!curr.isEmpty() && prevdepth == 1) {
                stuffs.add(new ArrayList<>(curr));
                curr.clear();
            }
        }

        if (depth != 0) {
            throw new ParsingError("mismatched curly braces"); // shouldnt be thrown be whatever
        }

        if (!curr.isEmpty()) {
            stuffs.add(curr);
        }

        return stuffs;
    }

    public Program parse() {
        // TODO: actually print out the right error
        if (!matchParen()) throw new ParsingError("mismatched parentheses/brackets/curlies");

        // change later
        Program root = new Program(new ArrayList<>(), functions);
        parse(tokens, root);
        return root;
    }

    private void parse(List<Token> tokens, Statement parent) {
        List<List<Token>> toks = children(tokens);

        for (List<Token> sub : toks) {
            int size = sub.size();

            if (!sub.isEmpty() && sub.get(size - 1).getType() == TokenType.SEMI) {
                // statement
                if (size < 2) {
                    return;
                }

                List<Token> stuffs = sub.subList(0, size - 1);

                Token first = stuffs.get(0);

                if (Token.isTypeIdentifier(first.getType())) {
                    // declaration
                    Token identifier = stuffs.get(1);

                    if (identifier.getType() != TokenType.IDENTFIER) {
                        // TODO: fix this error as well
                        throw new ParsingError("expected identifier, got something else");
                    }

                    if (stuffs.size() == 2) {
                        // just declaration
                        // ok so this thing parses the whole statement (i.e. int i = 5) so store the right part in a value and pass it along with left side
                        parent.addChild(new Declaration(stuffs.subList(0, 2).stream().map(Token::getValue).collect(Collectors.toList())));
                    } else if (stuffs.size() == 3) {
                        // not supposed to happen, something like int x = or int x 5
                        throw new ParsingError("expected argument");
                    } else if (stuffs.size() > 3) {
                        // hopefully legal
                        Value<?> res = Shunting.evaluate(Shunting.shunt(stuffs, memory));
                        List<Value<?>> prefix = stuffs.subList(0, 2).stream().map(Token::getValue).collect(Collectors.toList());
                        prefix.add(res);
                        parent.addChild(new Declaration(prefix));
                    }
                } else if (first.getType() == TokenType.IDENTFIER) {
                    if (stuffs.get(1).getType() == TokenType.LEFTPAREN) {
                        int lc = 1;
                        int rc = 0;

                        int start = 2;
                        int end = -1;
                        for (int i = 2; i < stuffs.size(); i++) {
                            TokenType thing = stuffs.get(i).getType();

                            if (thing == TokenType.LEFTPAREN) lc++;
                            else if (thing == TokenType.RIGHTPAREN) rc++;

                            if (lc == rc) {
                                end = i;
                                break;
                            }
                        }

                        Value<?> tingVal = Shunting.evaluate(Shunting.shunt(stuffs.subList(start, end), memory));

                        List<Value<?>> thing = new ArrayList<>();
                        thing.add(first.getValue());
                        if (start != end) {
                            thing.add(tingVal);
                        }
                        parent.addChild(new FunctionCall(thing));
                    }
                }
            } else {
                // function def requires at least 4 tokens
                if (size < 4) {
                    throw new ParsingError("not sure what you did here buddy");
                }

                Token type = sub.get(0);
                Token name = sub.get(1);

                if (Token.isTypeIdentifier(type.getType())) {
                    // function?????

                    if (name.getType() != TokenType.IDENTFIER) {
                        throw new ParsingError("expected type identifier");
                    }

                    // counting sublist
                    int start = -1;
                    int end = -1;
                    // curly count
                    int c = 0;
                    // index
                    int i = 0;
                    for (Token t : sub) {
                        TokenType tt = t.getType();

                        if (tt == TokenType.LEFTCURLY) {
                            if (c == 0) {
                                start = i;
                            }
                            c++;
                        } else if (tt == TokenType.RIGHTCURLY) {
                            c--;

                            if (c < 0) throw new ParsingError("unmatched curly braces");
                            else if (c == 0) {
                                end = i;
                                break;
                            }
                        }

                        i++;
                    }

                    if (sub.get(2).getType() == TokenType.LEFTCURLY) {
                        // no arg func
                        List<Token> subtokens = sub.subList(start + 1, end);

                        FunctionDeclaration func = new FunctionDeclaration(
                                List.of(type.getValue(), name.getValue())
                        );

                        parse(subtokens, func);

                        NeonFunction nf = func.getFunction();
                        functions.put(((IdentifierValue) name.getValue()).getValue(), nf);

                        // tokens within the function
                    }
                } else {
                    // if statement, just ignore for now
                }
            }
        }
    }

    /**
     * @return If the tokens have matching parentheses, brackets, and curly braces
     */
    private boolean matchParen() {
        int leftPCount = 0;
        int leftBCount = 0;
        int leftCCount = 0;
        int rightPCount = 0;
        int rightBCount = 0;
        int rightCCount = 0;

        for (Token token : tokens) {
            switch (token.getType()) {
                case LEFTPAREN:
                    leftPCount++;
                    break;
                case LEFTBRACKET:
                    leftBCount++;
                    break;
                case LEFTCURLY:
                    leftCCount++;
                    break;
                case RIGHTPAREN:
                    rightPCount++;
                    break;
                case RIGHTBRACKET:
                    rightBCount++;
                    break;
                case RIGHTCURLY:
                    rightCCount++;
                    break;
            }
        }

        return leftPCount == rightPCount && leftBCount == rightBCount && leftCCount == rightCCount;
    }
}
