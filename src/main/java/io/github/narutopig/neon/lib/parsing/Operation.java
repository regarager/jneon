package io.github.narutopig.neon.lib.parsing;

import io.github.narutopig.neon.lib.token.Token;

public class Operation {
    private final Token left;
    private final Token right;
    private final Token operation;

    /**
     * @param left
     * @param right     Can be null
     * @param operation
     */
    public Operation(Token left, Token right, Token operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public String toString() {
        return String.format("Operation{left: %s, right: %s, operation: %s}", left, right, operation);
    }
}
