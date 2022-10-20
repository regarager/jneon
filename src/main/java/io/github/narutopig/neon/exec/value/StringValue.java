package io.github.narutopig.neon.exec.value;

/**
 * Represents a string literal
 */
public class StringValue extends Value<String> {
    public StringValue(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return String.format("StringValue{\"%s\"}", getValue());
    }
}
