package io.github.narutopig.neon.parsing;

import io.github.narutopig.neon.parsing.token.Token;

import java.util.List;

public class Parsing {
    private final List<Token> tokens;

    public Parsing(List<Token> tokens) {
        this.tokens = tokens;
    }
}
