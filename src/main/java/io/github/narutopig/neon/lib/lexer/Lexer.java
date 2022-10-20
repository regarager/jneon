package io.github.narutopig.neon.lib.lexer;

import io.github.narutopig.neon.lib.error.ParsingError;
import io.github.narutopig.neon.lib.token.Token;
import io.github.narutopig.neon.lib.token.TokenType;
import io.github.narutopig.neon.lib.util.Chars;
import io.github.narutopig.neon.lib.util.Identifiers;
import io.github.narutopig.neon.lib.value.BooleanValue;
import io.github.narutopig.neon.lib.value.IdentifierValue;
import io.github.narutopig.neon.lib.value.NumberValue;
import io.github.narutopig.neon.lib.value.StringValue;

import java.util.ArrayList;
import java.util.List;

import static io.github.narutopig.neon.lib.util.Chars.*;

public class Lexer {
    private final char[] text;
    private final int textLength;
    private final Position position = new Position();
    private char currentChar;

    public Lexer(String text) {
        this.text = text.toCharArray();
        this.textLength = text.length();
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        advance();

        while (position.pos < textLength) {
            TokenType sct = singleCharToken(currentChar);

            if (Chars.isDigit(currentChar)) {
                NumberValue value = addNum();

                tokens.add(new Token(TokenType.NUMBERVALUE, value));
            } else if (currentChar == '"') {
                StringValue value = addString();

                tokens.add(new Token(TokenType.STRINGVALUE, value));

                advance();
            } else if (currentChar == '_' || isAlpha(currentChar)) {
                IdentifierValue value = addIdentifier();

                String identifier = value.getValue();

                TokenType tt = Identifiers.identifierMagic(identifier);

                if (tt == TokenType.BOOLEANVALUE) {
                    tokens.add(new Token(tt, new BooleanValue(identifier.equals("true"))));
                } else {
                    tokens.add(new Token(tt, value));
                }
            } else if (currentChar == ' ' || currentChar == '\n' || currentChar == '\t') {
                advance();
            } else if (sct != null) {
                if (sct == TokenType.DIVIDE && peek() == '/') {
                    while (currentChar != '\n') {
                        advance();
                    }

                    advance();
                }
                if (peek() == '=') {
                    switch (sct) {
                        case ASSIGN:
                            sct = TokenType.EQUAL;
                            advance();
                            break;
                        case GREATER:
                            sct = TokenType.GREATEREQ;
                            advance();
                            break;
                        case LESS:
                            sct = TokenType.LESSEQ;
                            advance();
                            break;
                        case NOT:
                            sct = TokenType.NOTEQ;
                            advance();
                            break;
                    }
                }

                tokens.add(new Token(sct, null));

                advance();
            }
        }

        return tokens;
    }

    public void test() {
        advance();

        while (position.pos < textLength) {
            System.out.print(currentChar);
            advance();
        }

        System.out.println();
    }

    private void advance() {
        position.advance(currentChar);

        if (position.pos >= textLength) return;
        currentChar = text[position.pos];
    }

    private char peek() {
        if (position.pos + 1 >= textLength) {
            return (char) -1;
        }
        return text[position.pos + 1];
    }

    private NumberValue addNum() {
        String num = "";
        int dotc = 0;

        while (position.pos < textLength && (isDigit(currentChar) || currentChar == '.')) {
            if (currentChar == '.') {
                if (dotc == 1) {
                    break;
                }
                dotc++;
            }

            num += currentChar;

            advance();
        }

        return new NumberValue(Double.parseDouble(num));
    }

    private StringValue addString() {
        String res = "";

        advance();

        while (currentChar != '"') {
            if (currentChar == '\n') {
                throw new ParsingError("end of line reached while parsing string on line " + position.line);
            } else if (position.pos >= textLength) {
                throw new ParsingError("end of file reached while parsing string" + position.line);
            }

            res += currentChar;
            advance();
        }

        return new StringValue(res);
    }

    private IdentifierValue addIdentifier() {
        String res = "";

        res += currentChar;

        advance();

        while (isAlpha(currentChar) || isDigit(currentChar)) {
            res += currentChar;

            advance();
        }

        return new IdentifierValue(res);
    }
}
