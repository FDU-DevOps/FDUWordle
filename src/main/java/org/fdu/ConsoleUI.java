package org.fdu;

import java.util.Scanner;

/**
 * Console user interface utility class.
 * Provides static methods for input/output.
 * This class should not be instantiated.
 */
public final class ConsoleUI {

    private static final Scanner SCANNER = new Scanner(System.in);

    // Prevent instantiation
    private ConsoleUI() { }

    /**
     * Prints a message followed by a newline.
     *
     * @param message message to print
     */
    public static void println(String message) {
        System.out.println(message);
    }

    /**
     * Prints a prompt and reads one line of input.
     *
     * @param prompt prompt text
     * @return raw input line
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine();
    }
}
