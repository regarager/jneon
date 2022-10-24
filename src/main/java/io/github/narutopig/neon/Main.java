package io.github.narutopig.neon;

import io.github.narutopig.neon.exec.functions.NeonFunction;
import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.statement.statements.Program;
import io.github.narutopig.neon.exec.value.NumberValue;
import io.github.narutopig.neon.exec.value.StringValue;
import io.github.narutopig.neon.parsing.Parsing;
import io.github.narutopig.neon.parsing.Shunting;
import io.github.narutopig.neon.parsing.lexer.Lexer;
import io.github.narutopig.neon.parsing.token.Token;
import io.github.narutopig.neon.parsing.token.TokenType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

        List<Token> tokens = lexer.tokenize();
        Parsing parsing = new Parsing(tokens);

        Program prog = parsing.parse();

        Memory memory = new Memory();

        NeonFunction mainFunc = prog.getFunctions().get("main");

        mainFunc.execute(new ArrayList<>(), memory);

        List<Token> test = List.of(
                new Token(TokenType.NUMBERVALUE, new NumberValue(3)),
                new Token(TokenType.STRINGVALUE, new StringValue("helo")),
                new Token(TokenType.MULTIPLY, null)
        );


        Memory mem = new Memory();

        Stack<Token> stuff = Shunting.shunt(test, mem);

        System.out.println(")(_ER*");
        System.out.println(stuff);
        System.out.println(Shunting.evaluate(stuff));
    }
}
