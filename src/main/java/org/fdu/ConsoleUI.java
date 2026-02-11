package org.fdu;

import java.util.Scanner;

/**
 * Console user interface utility class.
 * Responsibilities:
 * - Print messages to the console
 * - Read user input from the console
 *
 * This is a utility class: it must not be instantiated.
 */
public final class ConsoleUI {

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Prevents instantiation of this utility class.
     */
    private ConsoleUI() {
        // no instances allowed
    }

    /**
     * Prints a message followed by a newline.
     *
     * @param message message to print
     */
    public static void println(String message) {
        System.out.println(message);
    }

    /**
     * Prints a prompt and reads one full line of input.
     *
     * @param prompt prompt text to display
     * @return the raw line typed by the user (may be blank)
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine();
    }
}
