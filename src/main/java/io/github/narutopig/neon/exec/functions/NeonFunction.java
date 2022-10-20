package io.github.narutopig.neon.exec.functions;

import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.runtime.Type;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.value.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class NeonFunction {
    private final String name;
    private final boolean isStatic;
    private final List<Statement> statements;
    private final Type returnType;
    private final Memory memory;

    public NeonFunction(String name, boolean isStatic, List<Statement> statements, Type returnType, Memory memory) {
        this.name = name;
        this.isStatic = isStatic;
        this.statements = statements;
        this.returnType = returnType;
        this.memory = memory;
    }

    public NeonFunction(String name, boolean isStatic, List<Statement> statements, Type returnType) {
        this(name, isStatic, statements, returnType, new Memory());
    }

    // TODO: change return type to a Value
    public Type execute(List<Value<?>> arguments) {
        if (isStatic) {
            for (Statement statement : statements) {
                statement.execute(new Memory());
            }
        } else {
            for (Statement statement : statements) {
                statement.execute(memory);
            }
        }

        return returnType;
    }

    public String getName() {
        return name;
    }
}
