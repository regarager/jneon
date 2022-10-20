package io.github.narutopig.neon.exec.functions;

import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.runtime.Type;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.value.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class NeonFunction {
    private final String name;
    private final List<Statement> statements;
    private final Type returnType;

    public NeonFunction(String name, List<Statement> statements, Type returnType) {
        this.name = name;
        this.statements = statements;
        this.returnType = returnType;
    }

    public NeonFunction(String name, Type returnType) {
        this.name = name;
        this.statements = new ArrayList<>();
        this.returnType = returnType;
    }

    // TODO: change return type to a Value
    public Type execute(List<Value<?>> arguments, Memory memory) {
        for (Statement statement : statements) {
            statement.execute(memory);
        }

        return returnType;
    }

    public String getName() {
        return name;
    }
}
