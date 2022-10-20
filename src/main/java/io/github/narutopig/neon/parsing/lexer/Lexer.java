package io.github.narutopig.neon.parsing.lexer;

import io.github.narutopig.neon.exec.value.BooleanValue;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.NumberValue;
import io.github.narutopig.neon.exec.value.StringValue;
import io.github.narutopig.neon.parsing.ParsingError;
import io.github.narutopig.neon.parsing.token.Token;
import io.github.narutopig.neon.parsing.token.TokenType;
import io.github.narutopig.neon.parsing.util.Chars;
import io.github.narutopig.neon.parsing.util.Identifiers;

import java.util.ArrayList;
import java.util.List;

import static io.github.narutopig.neon.parsing.util.Chars.*;

public class Lexer {
    private final char[] text;
    private final int textLength;
    private final Position position = new Position();
    private char currentChar;

    public Lexer(String text) {
        this.text = text.toCharArray();
        this.textLength = text.length();
    }

    /**
     * Reads the content passed to the {@link Lexer}
     *
     * @return The tokens generated from the input string
     */
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

    /**
     * @return The subsequent characters that can form a number
     */
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

    /**
     * @return The characters until a " is encountered
     */
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

    /**
     * @return The characters until a non-alphanumeric character is encountered
     */
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
