package io.github.narutopig.neon.lib.exec;

import io.github.narutopig.neon.lib.value.Value;

public class Variable {
    private final String name;
    private final Types type;
    private Value<?> value;

    public Variable(String name, Value<?> value, Types type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Variable(String name, Types type) {
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

    public Types getType() {
        return type;
    }
}
