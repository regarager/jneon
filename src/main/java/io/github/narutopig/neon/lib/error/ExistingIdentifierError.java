package io.github.narutopig.neon.lib.error;

public class ExistingIdentifierError extends Error {
    public ExistingIdentifierError(String message) {
        super(message);
    }
}
