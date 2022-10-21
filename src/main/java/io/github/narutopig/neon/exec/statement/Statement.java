package io.github.narutopig.neon.exec.statement;

import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.value.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a basic statement
 */
public abstract class Statement {
    private final List<Value<?>> args;
    private final List<Statement> children = new ArrayList<>();

    public Statement(List<Value<?>> args) {
        this.args = args;
    }

    public abstract void execute(Memory memory);

    public List<Statement> getChildren() {
        return children;
    }

    public void addChild(Statement s) {
        children.add(s);
    }

    public String toString() {
        return String.format("Statement{args: %s}", args);
    }
}