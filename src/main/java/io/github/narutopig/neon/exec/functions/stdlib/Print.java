package io.github.narutopig.neon.exec.functions.stdlib;

import io.github.narutopig.neon.exec.UnknownIdentifierError;
import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.runtime.Type;
import io.github.narutopig.neon.exec.runtime.Variable;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.Value;

import java.util.List;

public class Print extends NeonFunction {
    private final Type returnType;

    public Print(String name, Type returnType) {
        super(name, returnType);
        this.returnType = returnType;
    }

    @Override
    public Type execute(List<Value<?>> arguments, Memory memory) {
        if (arguments.size() == 0) {
            System.out.println();
        } else {
            Value<?> arg = arguments.get(0);
            if (arg instanceof IdentifierValue) {
                IdentifierValue identifier = (IdentifierValue) arg;
                Variable variable = memory.getVariable(identifier.getValue());

                if (variable == null) {
                    throw new UnknownIdentifierError(String.format("identifier %s not found", identifier.getValue()));
                }

                arg = variable.getValue();
            }

            System.out.println(arg.getValue());
        }
        return returnType;
    }
}
