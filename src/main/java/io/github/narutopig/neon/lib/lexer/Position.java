package io.github.narutopig.neon.lib.lexer;

/**
 * Represents the current position of the lexer in the file
 */
public class Position {
    int line;
    int col;
    int pos;

    public Position() {
        line = 1;
        col = 0;
        pos = -1;
    }

    public void advance(char c) {
        if (c == '\n') {
            line++;
            col = 0;
        } else {
            col++;
        }
        pos++;
    }
}
