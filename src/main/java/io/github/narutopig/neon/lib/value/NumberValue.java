package io.github.narutopig.neon.lib.value;

public class NumberValue extends Value<Double> {
    public NumberValue(double value) {
        super(value);
    }

    @Override
    public String toString() {
        return String.format("NumberValue{%s}", getValue());
    }
}
