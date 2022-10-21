package io.github.narutopig.neon.parsing.util;

import io.github.narutopig.neon.exec.runtime.Type;
import io.github.narutopig.neon.exec.value.BooleanValue;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.TypeValue;
import io.github.narutopig.neon.exec.value.Value;
import io.github.narutopig.neon.parsing.token.TokenType;

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

    public static Value<?> valueMagic(String identifier) {
        if (identifier.equals("true")) {
            return new BooleanValue(true);
        } else if (identifier.equals("false")) {
            return new BooleanValue(false);
        }

        TokenType tt = typeToken(identifier);

        if (tt != null) {
            switch (tt) {
                case NUMBERTYPE: return new TypeValue(Type.NUMBER);
                case STRINGTYPE: return new TypeValue(Type.STRING);
                case BOOLEANTYPE: return new TypeValue(Type.BOOLEAN);
            }
        }

        return new IdentifierValue(identifier);
    }
}
