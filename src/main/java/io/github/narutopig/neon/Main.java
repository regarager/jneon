package io.github.narutopig.neon;

import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.statement.statements.Program;
import io.github.narutopig.neon.parsing.Parsing;
import io.github.narutopig.neon.parsing.lexer.Lexer;
import io.github.narutopig.neon.parsing.token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String file = args[0];

        Scanner scanner = new Scanner(new File(file));

        String content = "";

        while (scanner.hasNextLine()) {
            content += scanner.nextLine();
            content += "\n";
        }

        content = content.trim();

        Lexer lexer = new Lexer(content);

        List<Token> tokens = lexer.tokenize();
        Parsing parsing = new Parsing(tokens);

        Program prog = parsing.parse();

        Memory memory = new Memory();

        prog.getFunctions().get("main").execute(new ArrayList<>(), memory);
    }
}
