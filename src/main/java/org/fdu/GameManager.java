package org.fdu;

/**
 * Entry point into the game flow from main, processes player guesses,
 *   tracks game state and returns (or has queries) for game status, guess evaluations, etc.Controls the game flow: <br>
 * <p>
 * start game (constructor - selects secret word, initializes guesses, etc.)<br>
 * accept guesses (up to 6) to determine WIN / LOSS <br>
 * Uses WordRepo class for dictionary validation/answer selection <br>
 * Produces feedback results for ConsoleUI to display <br>
 * Tracks and makes visible game state (e.g. is game over, did the player win)
 *
 * @author tbd
 */

public class GameManager {

    private static final int MAX_GUESSES = 1;

    private final WordRepo repo;

    /**
     * Creates a GameManager that uses the given word repository.
     *
     * @param repo repository used to select the target word
     */
    public GameManager(WordRepo repo) {
        this.repo = repo;
    }

    /**
     * Runs a single game session (maximum 6 guesses).
     * End conditions:
     * - User guesses the word correctly
     * - User types QUIT
     * - User uses all 6 guesses (loss message shown)
     */
    public void runGame() {
        showIntro();

        String targetWord = repo.pickTargetWord(); // already UPPERCASE
        int guessesUsed = 0;

        while (guessesUsed < MAX_GUESSES) {
            String rawGuess = ConsoleUI.readLine("ENTER YOUR GUESS: ");
            String guess = normalize(rawGuess);

            // OTHER: blank input does not count as an attempt
            if (guess.isEmpty()) {
                ConsoleUI.println("PLEASE ENTER A GUESS (NOT BLANK).");
                continue;
            }

            // OTHER: quit command


            // This is a real attempt
            guessesUsed++;

            if (guess.equals(targetWord)) {
                ConsoleUI.println("CORRECT! YOU GUESSED THE WORD: " + targetWord);
                return;
            } else {
                int attemptsLeft = MAX_GUESSES - guessesUsed;
                if (attemptsLeft > 0) {
                    ConsoleUI.println("NOT CORRECT. ATTEMPTS LEFT: " + attemptsLeft);
                }
            }
        }

        // Out of guesses
        ConsoleUI.println("YOU LOST! THE CORRECT ANSWER WAS: " + targetWord);
    }

    /**
     * Displays the introduction messages for the game.
     */
    private void showIntro() {
        ConsoleUI.println("WELCOME TO WORDLE! GUESS THE SECRET WORD.");
        ConsoleUI.println("YOU HAVE 1 GUESS.");
    }

    /**
     * Normalizes user input according to the game rules:
     * - Trim leading/trailing whitespace
     * - Convert to UPPERCASE
     *
     * @param input raw user input (perhaps be null)
     * @return normalized string (never null; may be empty)
     */
    private String normalize(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().toUpperCase();
    }
}
