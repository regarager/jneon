package io.github.narutopig.neon.parsing;

import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.statement.statements.Declaration;
import io.github.narutopig.neon.parsing.token.Token;
import io.github.narutopig.neon.parsing.token.TokenType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Delayed;

public class Parsing {
    private final List<Token> tokens;
    private final List<Statement> statements = new ArrayList<>();
    private final Map<String, NeonFunction> functions = new HashMap<>();

    public Parsing(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        // TODO: actually print out the right error
        if (!matchParen()) throw new ParsingError("mismatched parentheses/brackets/curlies");

        // chnage later
        parse(tokens, null);
    }

    private void parse(List<Token> tokens, Statement parent) {
        List<List<Token>> toks = children(tokens);

        for (List<Token> sub : toks) {
            int size = sub.size();
            if (sub.get(size - 1).getType() == TokenType.SEMI) {
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
                        statements.add(new Declaration(List.of(identifier.getValue())));
                    } else if (stuffs.size() == 3) {
                        // not supposed to happen, something like int x = or int x 5
                        throw new ParsingError("expected argument");
                    }
                }
            } else {
                // function def requires at least 4 tokens
                if (size < 4) throw new ParsingError("not sure what you did here buddy");

                Token type = sub.get(0);
                Token name = sub.get(1);

                if (Token.isTypeIdentifier(type.getType())) {
                    // function?????

                    if (name.getType() != TokenType.IDENTFIER) {
                        throw new ParsingError("expected type identifier");
                    }


                } else {
                    // if statement, just ignore for now
                }
            }
        }
    }

    private List<List<Token>> children(List<Token> toks) {
        List<List<Token>> stuffs = new ArrayList<>();

        List<Token> curr = new ArrayList<>();

        int depth = 0; // for curly braces
        for (Token token : toks) {
            curr.add(token);

            TokenType tt = token.getType();
            if (tt == TokenType.SEMI && depth != 0 && !curr.isEmpty()) {
                stuffs.add(curr);
                curr.clear();
            } else if (tt == TokenType.LEFTCURLY) {
                depth++;
            } else if (tt == TokenType.RIGHTCURLY) {
                depth--;
            }

            if (!curr.isEmpty() && depth == 0) {
                stuffs.add(curr);
                curr.clear();
            }
        }

        if (depth != 0) {
            throw new ParsingError("mismatched curly braces"); // shouldnt be thrown be whatever
        }

        return stuffs;
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
