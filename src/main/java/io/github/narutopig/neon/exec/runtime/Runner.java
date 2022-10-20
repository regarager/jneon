package io.github.narutopig.neon.exec.runtime;

import io.github.narutopig.neon.parsing.token.Token;
import io.github.narutopig.neon.exec.value.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner implements Runnable {
    private final Map<String, Value<?>> memory = new HashMap<>();
    private final List<Token> tokens;

    public Runner(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public void run() {

    }
}
