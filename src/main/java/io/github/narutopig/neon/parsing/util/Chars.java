package io.github.narutopig.neon.parsing.util;

import io.github.narutopig.neon.parsing.token.TokenType;

public class Chars {
    /**
     * Checks if the char is a digit
     *
     * @param c
     */
    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Checks if the char is a Latin letter
     *
     * @param c
     */
    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * Attempts to convert the character into a {@link TokenType} that is only one character in length
     * These {@link TokenType} include ( ) [ ] + - etc.
     *
     * @param c
     */
    public static TokenType singleCharToken(char c) {
        switch (c) {
            case '(':
                return TokenType.LEFTPAREN;
            case ')':
                return TokenType.RIGHTPAREN;
            case '[':
                return TokenType.LEFTBRACKET;
            case ']':
                return TokenType.RIGHTBRACKET;
            case '{':
                return TokenType.LEFTCURLY;
            case '}':
                return TokenType.RIGHTCURLY;
            case ',':
                return TokenType.COMMA;
            case '.':
                return TokenType.PERIOD;
            case ';':
                return TokenType.SEMI;
            case '+':
                return TokenType.ADD;
            case '-':
                return TokenType.SUBTRACT;
            case '*':
                return TokenType.MULTIPLY;
            case '/':
                return TokenType.DIVIDE;
            case '%':
                return TokenType.MODULUS;
            case '=':
                return TokenType.ASSIGN;
            case '>':
                return TokenType.GREATER;
            case '<':
                return TokenType.LESS;
            case '!':
                return TokenType.NOT;
            case '&':
                return TokenType.AND;
            case '|':
                return TokenType.OR;
        }

        return null;
    }
}
