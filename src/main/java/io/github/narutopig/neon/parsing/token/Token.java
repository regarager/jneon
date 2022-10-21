package io.github.narutopig.neon.parsing.token;

import io.github.narutopig.neon.exec.value.Value;

public class Token {
    public static final TokenType[] ARITHOPS = new TokenType[]{
            TokenType.ADD,
            TokenType.SUBTRACT,
            TokenType.MULTIPLY,
            TokenType.DIVIDE,
            TokenType.MODULUS,
    };

    public static final TokenType[] TYPES = new TokenType[]{
            TokenType.NUMBERTYPE,
            TokenType.STRINGTYPE,
            TokenType.BOOLEANTYPE,
    };

    private final TokenType tokentype;
    private final Value<?> value;

    public Token(TokenType tokenType, Value<?> value) {
        this.tokentype = tokenType;
        this.value = value;
    }

    /**
     * Checks if the given {@link TokenType} is an arithmetic operator (+, -, *, / %)
     */
    public static boolean isArithOP(TokenType tt) {
        for (TokenType t : ARITHOPS) {
            if (tt == t) return true;
        }

        return false;
    }

    /**
     * Checks if the given {@link TokenType} is a typing identifier (number, string, boolean)
     */
   public static boolean isTypeIdentifier(TokenType tt) {
        for (TokenType t : TYPES) {
            if (tt == t) return true;
        }

        return false;
    }

    /**
     * Returns the precedence of the given {@link TokenType}
     *
     * @return 0 if the {@link TokenType} is + or -, 1 if it is *, /, or %, and -1 otherwise
     */
    public static int precedence(TokenType tt) {
        if (tt == TokenType.ADD || tt == TokenType.SUBTRACT) return 0;
        else if (tt == TokenType.MULTIPLY || tt == TokenType.DIVIDE || tt == TokenType.MODULUS) return 1;
        return -1;
    }

    public String toString() {
        return String.format("Token{TokenType: %s, Value: %s}", tokentype, value);
    }

    public TokenType getType() {
        return tokentype;
    }

    public Value<?> getValue() {
        return value;
    }
}