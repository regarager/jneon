package io.github.narutopig.neon.lib.parsing;

import io.github.narutopig.neon.lib.token.Token;

public class Expression {
    Operation left;
    Operation right;
    Token operation;

    public Expression(Operation left, Operation right, Token operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public String toString() {
        return String.format("Expression{left: %s, right: %s, operation: %s}", left, right, operation);
    }
}
