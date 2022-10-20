package io.github.narutopig.neon.lib.exec;

import io.github.narutopig.neon.lib.error.ExistingIdentifierError;
import io.github.narutopig.neon.lib.error.IllegalTypeError;
import io.github.narutopig.neon.lib.value.*;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    private final Map<String, Variable> state = new HashMap<>();

    public Variable getVariable(String key) {
        return state.get(key);
    }

    public void createVariable(String key, Value<?> value, Types type) {
        if (exists(key)) {
            throw new ExistingIdentifierError(String.format("Identifier %s already exists", key));
        }

        if (!typeCheck(value, type)) {
            throw new IllegalTypeError(String.format("cannot assign value of %s to type %s", value, type));
        }

        state.put(key, new Variable(key, value, type));
    }

    public void createVariable(String key, Types type) {
        createVariable(key, null, type);
    }

    public void setValue(String key, Value<?> value) {
        Types type = state.get(key).getType();

        if (!typeCheck(value, type)) {
            throw new IllegalTypeError(String.format("cannot assign value of %s to type %s", value, type));
        }

        state.get(key).setValue(value);
    }

    public boolean exists(String key) {
        return state.containsKey(key);
    }

    public boolean typeCheck(Value<?> value, Types type) {
        if (value instanceof BooleanValue) {
            return type == Types.BOOLEAN;
        } else if (value instanceof IdentifierValue) {
            IdentifierValue val = (IdentifierValue) value;
            return typeCheck(getVariable(val.getValue()).getValue(), type);
        } else if (value instanceof NumberValue) {
            return type == Types.NUMBER;
        } else if (value instanceof StringValue) {
            return type == Types.STRING;
        }

        // change later
        return true;
    }
}
