package io.github.narutopig.neon.exec.value;

import io.github.narutopig.neon.exec.runtime.Variable;

import java.util.function.Function;

/**
 * Represents the value of a variable (contains a function which fetches the value of the function)
 */
public class VariableValue extends Value<Function<String, Variable>> {
    public VariableValue(Function<String, Variable> value) {
        super(value);
    }

    @Override
    public Function<String, Variable> getValue() {
        return super.getValue();
    }
}
