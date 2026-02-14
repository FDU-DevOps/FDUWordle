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

    /**
     * Checks whether the user guess is correct or not <br>
     <p>
     Scope: Determining if winning condition is met (word match). Assume word is valid </p>
     @param playerGuess - user guess for the word
     @param targetWord - chosen answer for this wordle game
     @return - boolean flag to show if guess was correct or not
     @author - Xavier Orrala
     */
    public static boolean isWinningGuess(String playerGuess, String targetWord)
    {
        return playerGuess.equalsIgnoreCase(targetWord);
    }

}
