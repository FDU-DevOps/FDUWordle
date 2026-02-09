package org.fdu;

/**
 * Controls the game flow: <br>
 * start game <br>
 * accept guesses (up to 6) to determine WIN / LOSS <br>
 * Uses WordRepo for dictionary validation/answer selection <br>
 * Produces feedback results for ConsoleUI to display <br>
 */

public class GameManager {
    private final ConsoleUI ui;
    private final WordRepo repo;

    private static final int MAX_GUESSES = 6;
    /**
     * Creates a GameManager with required dependencies.
     * Objective: Connect UI and word source to control the game flow.
     * Scope: Called once at startup from App.
     *
     * @param ui ConsoleUI used to prompt and display messages
     * @param repo WordRepository used to pick the target word
     */
    public GameManager(ConsoleUI ui, WordRepo repo) {
        this.ui = ui;
        this.repo = repo;
    }

    /**
     * Runs the full game session.
     * Objective:
     *  - Display intro message
     *  - Pick a target word from the static list
     *  - Prompt the user for guesses repeatedly
     *  - Print whether the guess is correct or not
     * Scope:
     *  - Ends when guess is correct OR user types "quit"
     *  - User guesses do not have to be valid words
     *  - Case-insensitive, trims leading/trailing whitespace
     */
    public void runGame() {
        showIntro();

        String targetWord = repo.pickTargetWord();// stored lowercase
        int guessesUsed=0; //initialize

        while (guessesUsed < MAX_GUESSES) {
            String rawGuess = ui.readLine("Enter your guess: ");

            String guess = normalize(rawGuess);

            // Other: blank input
            if (guess.isEmpty()) {
                ui.println("Please enter a guess (not blank).");
                continue;
            }
            // Other: quit command
            if (guess.equals("QUIT")) {
                ui.println("Goodbye!");
                return;
            }

            guessesUsed++;
            // Correct / Incorrect
            if (guess.equals(targetWord)) {
                ui.println("Correct! You guessed the word: " + targetWord);
                return;
            } else {
                ui.println("Not correct. Attempts left:" + (MAX_GUESSES - guessesUsed));
            }


        }
        ui.println("YOU LOST!!, THE CORRECT WORD WAS:" + targetWord);
    }

    /**
     * Prints the intro messages.
     * Objective: Explain what to do and how to quit.
     * Scope: Called once per game at the start.
     *
     */
    private void showIntro() {
        ui.println("Welcome to Wordle (Lite)! Guess the secret word.");
        ui.println("Type 'quit' to exit.");
    }

    /**
     * Normalizes user input for comparison.
     * Objective: Make comparisons case-insensitive and whitespace-tolerant.
     * Scope:
     *  - Trims leading/trailing whitespace
     *  - Converts to Uppercase
     *
     * @param input the raw user input (perhaps null/blank)
     * @return normalized string (never null; may be empty)
     */
    private String normalize(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().toUpperCase();
    }
}
