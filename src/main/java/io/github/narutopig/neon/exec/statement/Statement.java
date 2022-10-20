package io.github.narutopig.neon.exec.statement;

import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.value.Value;

import java.util.List;

/**
 * Represents a basic statement
 */
public abstract class Statement {
    private final List<Value<?>> args;

    public Statement(List<Value<?>> args) {
        this.args = args;
    }

    public abstract void execute(Memory memory);
}