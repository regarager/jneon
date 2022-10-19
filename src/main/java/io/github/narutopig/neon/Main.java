package io.github.narutopig.neon;

import io.github.narutopig.neon.lib.lexer.Lexer;
import io.github.narutopig.neon.lib.token.Token;

import java.io.File;
import java.io.FileNotFoundException;
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

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
