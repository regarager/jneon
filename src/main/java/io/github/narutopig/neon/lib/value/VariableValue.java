package io.github.narutopig.neon.lib.value;

import io.github.narutopig.neon.lib.exec.Variable;

import java.util.function.Function;

public class VariableValue extends Value<Function<String, Variable>> {
    public VariableValue(Function<String, Variable> value) {
        super(value);
    }

    @Override
    public Function<String, Variable> getValue() {
        return super.getValue();
    }
}
