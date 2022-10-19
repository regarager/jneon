package io.github.narutopig.neon.lib.value;

public abstract class Value<T> {
    private final T value;

    public Value(T value) {
        this.value = value;
    }

    public String toString() {
        return String.format("Value{%s}", value);
    }

    public T getValue() {
        return value;
    }
}
