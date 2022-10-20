package io.github.narutopig.neon.lib.parsing;

import io.github.narutopig.neon.lib.error.ParsingError;
import io.github.narutopig.neon.lib.token.Token;
import io.github.narutopig.neon.lib.token.TokenType;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Shunting {
    public static Stack<Token> shunt(List<Token> tokens) {
        Stack<Token> output = new Stack<>();
        Stack<Token> operator = new Stack<>();

        int length = tokens.size();
        for (int i = 0; i < length; i++) {
            Token token = tokens.get(i);

            if (token.getType() == TokenType.NUMBERVALUE) {
                output.push(token);
            } else if (token.getType() == TokenType.IDENTFIER) {
                if (i + 1 < length && tokens.get(i + 1).getType() == TokenType.LEFTPAREN) {
                    operator.push(token);
                } else {
                    output.push(token);
                }
            } else if (Token.isArithOP(token.getType())) {
                if (operator.isEmpty()) continue;
                Token o2 = operator.peek();

                while (o2.getType() != TokenType.LEFTPAREN && Token.precedence(o2.getType()) > Token.precedence(token.getType())) {
                    Token top;

                    try {
                        top = operator.pop();
                    } catch (EmptyStackException e) {
                        throw new ParsingError("mismatched parentheses");
                    }

                    output.push(top);

                    o2 = operator.peek();
                }

                operator.push(token);
            } else if (token.getType() == TokenType.LEFTPAREN) {
                operator.push(token);
            } else if (token.getType() == TokenType.RIGHTPAREN) {
                while (!operator.isEmpty() && operator.peek().getType() != TokenType.LEFTPAREN) {
                    if (operator.isEmpty()) {
                        throw new ParsingError("mismatched parentheses");
                    }
                    output.push(operator.pop());
                }

                if (!operator.isEmpty() && operator.peek().getType() != TokenType.LEFTPAREN) {
                    throw new ParsingError("mismatched parentheses");
                }


                if (!operator.isEmpty()) operator.pop();

                if (!operator.isEmpty() && operator.peek().getType() == TokenType.IDENTFIER) {
                    output.push(operator.pop());
                }
            }
        }

        while (!operator.isEmpty()) {
            Token front = operator.peek();

            if (front.getType() == TokenType.LEFTPAREN || front.getType() == TokenType.RIGHTPAREN) {
                throw new ParsingError("mismatched parentheses");
            }

            output.push(operator.pop());
        }

        return output;
    }
}
