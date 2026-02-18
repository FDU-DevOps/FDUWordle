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

    /**
     * Enum to store ANSI Escape Codes for Color Coded Word Feedback <br>
     <p>
     Scope: 3 enums: GREEN, YELLOW, RESET
     </p>
     @author Xavier Orrala
     */
    public enum FeedbackType {
        // Reset Escape Code
        RESET("\033[0m"),

        // Color Escape Code
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m");

        private final String code;
        // Private Constructor
        FeedbackType(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    /**
     * Display feedback to the user, including: color-coded word feedback, if user has won, or if user is out of guesses<br>
     <p> Scope:
     Display UserGuess enums as color-coded output (Green, Yellow, Gray) to the console.
     Handles the game state message (Is game over, is it still going, is user out of guesses)
     </p>
     @param feedback - enum array that holds the user guess, each letter is broken down as an enum
     @param playerGuess - the word the player guessed (to display the actual letters)
     @author Emirlan Asanakunov
     */

    public static void DisplayGuessResult(FeedbackType[] feedback,String playerGuess) {
        // Convert playerGuess to uppercase for display
        char[] playerGuessLetters = playerGuess.toUpperCase().toCharArray();

        // Display each letter with its corresponding color
        for(int i = 0; i < feedback.length; i++)
        {
            // Display: [color code] + [letter] + [reset code] + [space]
            // Example: "green" + "O" + "gray" + " " -> displays green "O "
            System.out.print(feedback[i].toString() + playerGuessLetters[i] + FeedbackType.RESET + " ");
        }
        System.out.println();
    }
}
