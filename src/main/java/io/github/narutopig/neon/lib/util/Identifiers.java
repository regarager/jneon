package io.github.narutopig.neon.lib.util;

import io.github.narutopig.neon.lib.token.TokenType;

public class Identifiers {
    public static TokenType typeToken(String identifier) {
        switch (identifier) {
            case "number":
                return TokenType.NUMBERTYPE;
            case "string":
                return TokenType.STRINGTYPE;
            case "boolean":
                return TokenType.BOOLEANTYPE;
        }

        return null;
    }

    public static TokenType identifierMagic(String identifier) {
        if (identifier.equals("true")) {
            return TokenType.BOOLEANVALUE;
        } else if (identifier.equals("false")) {
            return TokenType.BOOLEANVALUE;
        }

        TokenType tt = typeToken(identifier);

        if (tt != null) return tt;

        return TokenType.IDENTFIER;
    }
}
