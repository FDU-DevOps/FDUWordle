package org.fdu;

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
public class ConsoleUI {

    /**
     * Enum to store ANSI Escape Codes for Color Coded Word Feedback <br>
     <p>
     Scope: 3 enums: GREEN, YELLOW, RESET
     </p>
     @author (developer name)
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
     @param isWin - boolean to track if user guess is correct
     @param guessCount - int to track number of user guesses left
     @param playerGuess - the word the player guessed (to display the actual letters)
     @return - nothing returned, just displaying results to console
     @author Emirlan Asanakunov
     */

    public static void DisplayGuessResult(FeedbackType[] feedback, boolean isWin, int guessCount, String playerGuess) {
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

        // Check if user has won
        if(isWin) { System.out.println("Congratulations! You guessed the word correctly!"); }
        // Check if user is out of guesses
        else if(guessCount == 0) { System.out.println("Game Over! You have run out of guesses."); }
        // Game is still ongoing
        else { System.out.println("Guesses remaining: " + guessCount); }
    }
}
