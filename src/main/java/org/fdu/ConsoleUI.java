package org.fdu;

import java.util.Scanner;

/**
 * Static utility class responsible for interacting with player (input, output),
 *   scope excludes: content (e.g. specific messages) and game logic <br>
 * <p>
 * Scope: <br>
 *   Reads input from player (e.g. guesses) <br>
 *   Writes output to player <br>
 *      (e.g. info messages such as intro, guess evaluations, game status and error messages)
 *
 * @author tbd
 */
public final class ConsoleUI {
    private static final Scanner SCANNER = new Scanner(System.in);

    // Prevent instantiation
    private ConsoleUI() { }

    /**
     * Prints a message followed by a newline. <br>
     * @param message message to print
     */
    public static void println(String message) {
        System.out.println(message);
    }

    /**
     * Prints a prompt and reads one line of input.<br>
     * @param prompt prompt text
     * @return raw input line
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine();
    }
}
