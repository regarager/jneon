package io.github.narutopig.neon;

import io.github.narutopig.neon.exec.runtime.Runner;

import java.io.File;
import java.io.FileNotFoundException;
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

        new Runner(content).run();
    }
}
