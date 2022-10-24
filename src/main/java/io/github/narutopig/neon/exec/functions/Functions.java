package io.github.narutopig.neon.exec.functions;

import io.github.narutopig.neon.exec.functions.stdlib.Print;
import io.github.narutopig.neon.exec.runtime.Type;

import java.util.HashMap;
import java.util.Map;

public class Functions {
    private static final Map<String, NeonFunction> functions = new HashMap<>();

    static {
        functions.put("print", new Print("print", Type.VOID));
    }

    public static NeonFunction getFunction(String key) {
        return functions.get(key);
    }

    public static Map<String, NeonFunction> getFunctions() {
        return functions;
    }
}
