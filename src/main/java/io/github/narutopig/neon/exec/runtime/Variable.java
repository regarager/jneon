package io.github.narutopig.neon.exec.runtime;

import io.github.narutopig.neon.exec.value.Value;

public class Variable {
    private final String name;
    private final Type type;
    private Value<?> value;

    public Variable(String name, Value<?> value, Type type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Variable(String name, Type type) {
        this(name, null, type);
    }

    public String getName() {
        return name;
    }

    public Value<?> getValue() {
        return value;
    }

    public void setValue(Value<?> value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }
}
