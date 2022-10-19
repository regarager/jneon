package io.github.narutopig.neon.lib.token;

import io.github.narutopig.neon.lib.value.Value;

public class Token {
    final TokenType tokentype;
    final Value value;

    public Token(TokenType tokenType, Value value) {
        this.tokentype = tokenType;
        this.value = value;
    }

    public String toString() {
        return String.format("Token{TokenType: %s, Value: %s}", tokentype, value);
    }
}