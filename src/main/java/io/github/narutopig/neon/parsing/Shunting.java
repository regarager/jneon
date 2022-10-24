package io.github.narutopig.neon.parsing;

import io.github.narutopig.neon.exec.runtime.Memory;
import io.github.narutopig.neon.exec.value.IdentifierValue;
import io.github.narutopig.neon.exec.value.Value;
import io.github.narutopig.neon.parsing.token.Token;
import io.github.narutopig.neon.parsing.token.TokenType;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Shunting {
    /**
     * Evaluates the output of shunt
     *
     * @param shunted The stack returned from shunt
     * @return A value
     * @throws ParsingError when operations between two values is unsupported
     */
    public static Value<?> evaluate(Stack<Token> shunted) {
        List<Token> list = new ArrayList<>(shunted);
        int size = list.size();
        Stack<Value<?>> stack = new Stack<>();

        for (int i = 0; i < size; i++) {
            Token token = list.get(i);

            TokenType tokenType = token.getType();
            if (tokenType == TokenType.NUMBERVALUE) {
                // variables are converted to nums in shunt
                stack.push(token.getValue());
            } else {
                // all identifiers are functions
                Value<?> val1 = stack.pop();
                Value<?> val2 = stack.pop();

                switch (tokenType) {
                    case ADD: {
                        stack.push(Operations.add(val1, val2));
                        break;
                    }
                    case SUBTRACT: {
                        stack.push(Operations.subtract(val1, val2));
                        break;
                    }
                    case MULTIPLY: {
                        stack.push(Operations.multiply(val1, val2));
                        break;
                    }
                    case DIVIDE: {
                        stack.push(Operations.divide(val1, val2));
                        break;
                    }
                    case MODULUS: {
                        stack.push(Operations.modulus(val1, val2));
                        break;
                    }
                }
            }
        }

        return stack.pop();
    }

    /**
     * Implements the <a href="https://en.wikipedia.org/wiki/Shunting_yard_algorithm">shunting-yard algorithm</a>
     *
     * @param tokens The list of the tokens in the expression
     * @return A stack of tokens in Reverse Polish Notation
     */
    public static Stack<Token> shunt(List<Token> tokens, Memory memory) {
        Stack<Token> output = new Stack<>();
        Stack<Token> operator = new Stack<>();

        int length = tokens.size();
        for (int i = 0; i < length; i++) {
            Token token = tokens.get(i);

            if (token.getType() == TokenType.NUMBERVALUE || token.getType() == TokenType.STRINGVALUE) {
                output.push(token);
            } else if (token.getType() == TokenType.IDENTFIER) {
                if (i + 1 < length && tokens.get(i + 1).getType() == TokenType.LEFTPAREN) {
                    operator.push(token);
                } else {
                    IdentifierValue identifierValue = (IdentifierValue) (token.getValue());
                    Value<?> varValue = memory.getVariable(identifierValue.getValue()).getValue();
                    output.push(new Token(TokenType.NUMBERVALUE, varValue));
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
