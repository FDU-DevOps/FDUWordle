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
}
