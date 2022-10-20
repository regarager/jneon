package io.github.narutopig.neon.exec.value;

/**
 * Represents a variable "pointer" (does not contain value)
 */
public class IdentifierValue extends Value<String> {
    public IdentifierValue(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return String.format("IdentifierValue{%s}", getValue());
    }
}
