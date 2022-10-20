package io.github.narutopig.neon.parsing;

import io.github.narutopig.neon.parsing.ParsingError;
import io.github.narutopig.neon.parsing.token.Token;
import io.github.narutopig.neon.parsing.token.TokenType;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Shunting {
    /**
     * Implements the <a href="https://en.wikipedia.org/wiki/Shunting_yard_algorithm">shunting-yard algorithm</a>
     *
     * @param tokens The list of the tokens in the expression
     * @return A stack of tokens in Reverse Polish Notation
     */
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
                while (!operator.isEmpty() && operator.peek().getType() != TokenType.LEFTPAREN && Token.precedence(operator.peek().getType()) >= Token.precedence(token.getType())) {
                    try {
                        output.push(operator.pop());
                    } catch (EmptyStackException e) {
                        throw new ParsingError("mismatched parentheses");
                    }
                }

                operator.push(token);
            } else if (token.getType() == TokenType.LEFTPAREN) {
                operator.push(token);
            } else if (token.getType() == TokenType.RIGHTPAREN) {
                while (!operator.isEmpty() && operator.peek().getType() != TokenType.LEFTPAREN) {
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
