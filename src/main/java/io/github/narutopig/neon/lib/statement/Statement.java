package io.github.narutopig.neon.lib.statement;

import io.github.narutopig.neon.lib.exec.Memory;
import io.github.narutopig.neon.lib.value.Value;

import java.util.List;

public abstract class Statement {
    private final List<Value<?>> args;

    public Statement(List<Value<?>> args) {
        this.args = args;
    }

    public abstract void execute(Memory memory);
}