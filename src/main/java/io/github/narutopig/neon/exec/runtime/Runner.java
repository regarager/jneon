package io.github.narutopig.neon.exec.runtime;

import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.statement.statements.Program;
import io.github.narutopig.neon.parsing.Parsing;
import io.github.narutopig.neon.parsing.lexer.Lexer;
import io.github.narutopig.neon.parsing.token.Token;

import java.util.ArrayList;
import java.util.List;

public class Runner implements Runnable {
    private final Memory memory = new Memory();
    private final String content;

    public Runner(String content) {
        this.content = content;
    }

    @Override
    public void run() {
        Lexer lexer = new Lexer(content);

        List<Token> tokens = lexer.tokenize();

        Parsing parsing = new Parsing(tokens, memory);

        Program prog = parsing.parse();

        NeonFunction mainFunc = prog.getFunctions().get("main");

        mainFunc.execute(new ArrayList<>(), memory);
    }
}
