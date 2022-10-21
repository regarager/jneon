package io.github.narutopig.neon.exec.statement.statements;

import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.statement.Statement;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.TypeValue;
import io.github.narutopig.neon.exec.value.Value;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeclaration extends Statement {
    private final List<Statement> children = new ArrayList<>();
    private final TypeValue type;
    private final IdentifierValue identifier;

    public FunctionDeclaration(List<Value<?>> args) {
        super(args);

        if (args.size() < 2) throw new Error("error (message coming soon)");

        TypeValue type = (TypeValue) args.get(0);
        IdentifierValue identifier = (IdentifierValue) args.get(1);

        this.type = type;
        this.identifier = identifier;
        // define function args later
    }

    @Override
    public void execute(Memory memory) {
        // no execution, just add getFunction to functions
    }

    public NeonFunction getFunction() {
        return new NeonFunction(identifier.getValue(), children, type.getValue());
    }
}
