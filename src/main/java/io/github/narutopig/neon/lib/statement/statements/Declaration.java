package io.github.narutopig.neon.lib.statement.statements;

import io.github.narutopig.neon.lib.exec.Memory;
import io.github.narutopig.neon.lib.exec.Types;
import io.github.narutopig.neon.lib.statement.Statement;
import io.github.narutopig.neon.lib.value.IdentifierValue;
import io.github.narutopig.neon.lib.value.TypeValue;
import io.github.narutopig.neon.lib.value.Value;

import java.util.List;

public class Declaration extends Statement {
    private final List<Value<?>> args;

    public Declaration(List<Value<?>> args) {
        super(args);
        this.args = args;
    }

    @Override
    public void execute(Memory memory) {
        TypeValue varType = (TypeValue) args.get(0);

        Types type = varType.getValue();

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
