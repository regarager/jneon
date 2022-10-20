package io.github.narutopig.neon.exec.statement.statements;

import io.github.narutopig.neon.exec.UnknownIdentifierError;
import io.github.narutopig.neon.exec.functions.Functions;
import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.Value;

import java.util.List;

public class FunctionCall extends Statement {
    private final List<Value<?>> args;

    public FunctionCall(List<Value<?>> args) {
        super(args);

        this.args = args;
    }

    @Override
    public void execute(Memory memory) {
        IdentifierValue val = (IdentifierValue) args.get(0);

        NeonFunction func = Functions.getFunction(val.getValue());

        if (func == null) {
            throw new UnknownIdentifierError(String.format("function %s not found", val.getValue()));
        }

        func.execute(args.subList(1, args.size()), memory);
    }
}
