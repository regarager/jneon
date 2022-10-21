package io.github.narutopig.neon.exec.statement.statements;

import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.value.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program extends Statement {
    private final List<Statement> children = new ArrayList<>();
    private final Map<String, NeonFunction> functions;

    public Program(List<Value<?>> args, Map<String, NeonFunction> funcs) {
        super(args);
        this.functions = funcs;
    }

    @Override
    public void execute(Memory memory) {
        for (Statement child : children) {
            child.execute(memory);
        }
    }

    public List<Statement> getChildren() {
        return children;
    }

    public Map<String, NeonFunction> getFunctions() {
        return functions;
    }

    public void addChild(Statement s) {
        children.add(s);
    }
}
