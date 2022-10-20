package io.github.narutopig.neon.exec.functions.stdlib;

import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.runtime.Type;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.value.Value;

import java.util.List;

public class Print extends NeonFunction {
    private final Type returnType;
    private final Memory memory;

    public Print(String name, boolean isStatic, List<Statement> statements, Type returnType, Memory memory) {
        super(name, isStatic, statements, returnType, memory);
        this.returnType = returnType;
        this.memory = memory;
    }

    @Override
    public Type execute(List<Value<?>> arguments) {
        if (arguments.size() == 0) {
            System.out.println();
        } else {
            Value<?> arg = arguments.get(0);
        }
        return returnType;
    }
}
