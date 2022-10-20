package io.github.narutopig.neon.exec.statement.statements;

import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.runtime.Type;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.TypeValue;
import io.github.narutopig.neon.exec.value.Value;

import java.util.List;

/**
 * Represents the declaration of a variable
 */
public class Declaration extends Statement {
    private final List<Value<?>> args;

    public Declaration(List<Value<?>> args) {
        super(args);
        this.args = args;
    }

    @Override
    public void execute(Memory memory) {
        TypeValue varType = (TypeValue) args.get(0);

        Type type = varType.getValue();

        IdentifierValue identifier = (IdentifierValue) args.get(1);

        String name = identifier.getValue();

        if (args.size() > 1) {
            Value<?> value = args.get(2);
            memory.createVariable(name, value, type);
        } else {
            memory.createVariable(name, type);
        }
    }
}
