package io.github.narutopig.neon.lib.token;

import io.github.narutopig.neon.lib.value.Value;

public class Token {
    public static final TokenType[] ARITHOPS = new TokenType[]{
            TokenType.ADD,
            TokenType.SUBTRACT,
            TokenType.MULTIPLY,
            TokenType.DIVIDE,
            TokenType.MODULUS,
    };
    private final TokenType tokentype;
    private final Value value;

    public Token(TokenType tokenType, Value value) {
        this.tokentype = tokenType;
        this.value = value;
    }

    public static boolean isArithOP(TokenType tt) {
        for (TokenType t : ARITHOPS) {
            if (tt == t) return true;
        }

        return false;
    }

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

    public Value getValue() {
        return value;
    }
}