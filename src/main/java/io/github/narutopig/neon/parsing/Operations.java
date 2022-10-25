package io.github.narutopig.neon.parsing;

import io.github.narutopig.neon.errors.runtime.MathError;
import io.github.narutopig.neon.exec.value.NumberValue;
import io.github.narutopig.neon.exec.value.StringValue;
import io.github.narutopig.neon.exec.value.Value;

public class Operations {
    // TODO: print types of the illegal values for operations
    public static Value<?> add(Value<?> val1, Value<?> val2) {
        if (val1 instanceof NumberValue) {
            if (val2 instanceof NumberValue) {
                return new NumberValue(((NumberValue) val1).getValue() + ((NumberValue) val2).getValue());
            } else if (val2 instanceof StringValue) {
                return new StringValue(
                        String.format("%f%s", ((NumberValue) val1).getValue(), ((StringValue) val2).getValue())
                );
            } else {
                throw new MathError("operation add not supported");
            }
        } else if (val1 instanceof StringValue) {
            if (val2 instanceof NumberValue) {
                return new StringValue(
                        String.format("%s%f", ((StringValue) val1).getValue(), ((NumberValue) val2).getValue())
                );
            } else if (val2 instanceof StringValue) {
                return new StringValue(
                        ((StringValue) val2).getValue() + ((StringValue) val1).getValue()
                );
            } else {
                throw new MathError("operation add not supported");
            }
        } else {
            throw new MathError("operation add not supported");
        }
    }

    public static Value<?> subtract(Value<?> val1, Value<?> val2) {
        if (val1 instanceof NumberValue) {
            if (val2 instanceof NumberValue) {
                return new NumberValue(((NumberValue) val1).getValue() - ((NumberValue) val2).getValue());
            } else {
                throw new MathError("operation subtract not supported");
            }
        } else {
            throw new MathError("operation subtract not supported");
        }
    }

    public static Value<?> multiply(Value<?> val1, Value<?> val2) {
        if (val1 instanceof NumberValue) {
            if (val2 instanceof NumberValue) {
                return new NumberValue(((NumberValue) val1).getValue() * ((NumberValue) val2).getValue());
            } else {
                throw new MathError("operation add not supported");
            }
        } else if (val1 instanceof StringValue) {
            if (val2 instanceof NumberValue) {
                double val2thing = (double) val2.getValue();
                if (val2thing % 1 != 0) {
                    throw new MathError("operation multiplication between string and number must be string and int");
                }
                if (val2thing < 0) {
                    throw new MathError("operation multiplication between string and number most be positive");
                }
                String temp = "";

                for (int i = 0; i < (int) val2thing; i++) {
                    temp += ((StringValue) val1).getValue();
                }

                return new StringValue(temp);
            } else {
                throw new MathError("operation multiplication not supported");
            }
        } else {
            throw new MathError("operation multiplication not supported");
        }
    }

    public static Value<?> divide(Value<?> val1, Value<?> val2) {
        if (val1 instanceof NumberValue) {
            if (val2 instanceof NumberValue) {
                return new NumberValue(((NumberValue) val1).getValue() / ((NumberValue) val2).getValue());
            } else {
                throw new MathError("operation add not supported");
            }
        } else {
            throw new MathError("operation add not supported");
        }
    }

    public static Value<?> modulus(Value<?> val1, Value<?> val2) {
        if (val1 instanceof NumberValue) {
            if (val2 instanceof NumberValue) {
                return new NumberValue(((NumberValue) val1).getValue() % ((NumberValue) val2).getValue());
            } else {
                throw new MathError("operation modulus not supported");
            }
        } else {
            throw new MathError("operation modulus not supported");
        }
    }
}
