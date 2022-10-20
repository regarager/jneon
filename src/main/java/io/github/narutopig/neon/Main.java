package io.github.narutopig.neon;

import io.github.narutopig.neon.lib.lexer.Lexer;
import io.github.narutopig.neon.lib.parsing.Shunting;
import io.github.narutopig.neon.lib.token.Token;
import io.github.narutopig.neon.lib.token.TokenType;
import io.github.narutopig.neon.lib.value.IdentifierValue;
import io.github.narutopig.neon.lib.value.NumberValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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

        List<Token> test = List.of(
                new Token(TokenType.IDENTFIER, new IdentifierValue("sin")),
                new Token(TokenType.LEFTPAREN, null),
                new Token(TokenType.IDENTFIER, new IdentifierValue("max")),
                new Token(TokenType.NUMBERVALUE, new NumberValue(2)),
                new Token(TokenType.NUMBERVALUE, new NumberValue(3)),
                new Token(TokenType.RIGHTPAREN, null),
                new Token(TokenType.DIVIDE, null),
                new Token(TokenType.NUMBERVALUE, new NumberValue(3)),
                new Token(TokenType.MULTIPLY, null),
                new Token(TokenType.IDENTFIER, new IdentifierValue("pi")),
                new Token(TokenType.RIGHTPAREN, null)
        );

        Stack<Token> stuff = Shunting.shunt(test);

        for (Token t : stuff) {
            System.out.println(t);
        }

        /*
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
         */
    }
}
