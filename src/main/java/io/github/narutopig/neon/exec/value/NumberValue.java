package io.github.narutopig.neon.exec.value;

/**
 * Represents a number literal
 */
public class NumberValue extends Value<Double> {
    public NumberValue(double value) {
        super(value);
    }

    @Override
    public String toString() {
        return String.format("NumberValue{%s}", getValue());
    }
}
