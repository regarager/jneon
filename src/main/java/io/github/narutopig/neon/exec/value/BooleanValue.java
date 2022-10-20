package io.github.narutopig.neon.exec.value;

/**
 * Represents a boolean literal
 */
public class BooleanValue extends Value<Boolean> {
    public BooleanValue(Boolean value) {
        super(value);
    }

    @Override
    public String toString() {
        return String.format("BooleanValue{%s}", getValue());
    }
}
