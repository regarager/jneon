package io.github.narutopig.neon.exec.statement.statements;

import io.github.narutopig.neon.errors.runtime.UnknownIdentifierError;
import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.Value;

import java.util.List;

/**
 * Represents the assignment of a value to a variable
 */
public class Assignment extends Statement {
    private final List<Value<?>> args;

    public Assignment(List<Value<?>> args) {
        super(args);
        this.args = args;
    }

    @Override
    public void execute(Memory memory) {
        IdentifierValue identifier = (IdentifierValue) args.get(0);

        if (!memory.exists(identifier.getValue())) {
            throw new UnknownIdentifierError(String.format("identifier %s not found", identifier.getValue()));
        }

        Value<?> value = args.get(1);

        memory.setValue(identifier.getValue(), value);
    }
}
