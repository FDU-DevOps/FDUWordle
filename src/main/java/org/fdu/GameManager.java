package org.fdu;

/**
 * Entry point into the game flow from main, processes player guesses,
 *   tracks game state and returns (or has queries) for game status, guess evaluations, etc.Controls the game flow: <br>
 * <p>
 * start game (constructor - selects secret word, initializes guesses, etc.)<br>
 * accept guesses (up to 1) to determine WIN / LOSS <br>
 * Uses WordRepo class for dictionary validation/answer selection <br>
 * Produces feedback results for ConsoleUI to display <br>
 * Tracks and makes visible game state (e.g. is game over, did the player win)
 *
 * @author tbd
 */

public class GameManager {

    public static final int MAX_GUESSES = 3;
    private final String targetWord;
    private int guessesUsed = 0;


    /**
     * Creates a GameManager object
     * Maybe initialize guesses, etc.
     */
    public GameManager() {
        targetWord = WordRepo.pickTargetWord();
    }

    public String getTargetWord(){
        return targetWord;
    }

    public int getGuessesUsed(){
        return guessesUsed;
    }

    public int getMaxGuesses(){
        return MAX_GUESSES;
    }

    /**
     * doesGuessMatch(String norm_guess) - compares the normalized guess to the target word <br>
     * <p>
     * increments guesses the user has used
     * @param normGuess is normalized to uppercase without white space
     * @return  True if guess matches target word, false otherwise
     */

    public boolean doesGuessMatch(String normGuess){
        guessesUsed++;
        return normGuess.equals(targetWord);
    }

    /**
     * Normalizes user input according to the game rules:
     * - Trim leading/trailing whitespace
     * - Convert to UPPERCASE
     *
     * @param input raw user input (perhaps be null)
     * @return normalized string (never null; may be empty)
     */
    public String normalize(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().toUpperCase();
    }
}
