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

    public static final int MAX_GUESSES = 6;
    private final String targetWord;
    private int guessesUsed = 0;


    /**
     * GameManager() - Initializes the targetWord from the WordRepo Class method pickTargetWord() <br>
     * </p>
     * targetWord - (String) the correct answer to the game is picked and assigned to targetWord
     */
    public GameManager() {
        targetWord = WordRepo.pickTargetWord();
    }
    /**
     * getTargetWord() - Allows Game Manager object to access the target word <br>
     * @return targetWord - (String) randomly chosen word
     */
    public String getTargetWord(){
        return targetWord;
    }
    /**
     * getGuessedUsed() - Allows Game Manager object to access number of guesses user has made <br>
     * @return guessesUsed - (int) number of valid guesses the player has made
     */
    public int getGuessesUsed(){
        return guessesUsed;
    }
    /**
     * getMaxGuesses() - Allows Game Manager object to access number of max guesses a user has to guess the word <br>
     * @return MAX_GUESSES - (int) number of guesses the player is allowed
     */
    public int getMaxGuesses(){
        return MAX_GUESSES;
    }


    /**
     * doesGuessMatch(String norm_guess) - compares the normalized guess to the target word <br>
     * <p>
     * increments guesses the user has used
     * @param normalizedGuess is normalized to uppercase without white space
     * @return  True if guess matches target word, false otherwise
     */

    public boolean doesGuessMatch(String normalizedGuess){
        guessesUsed++;
        return normalizedGuess.equals(targetWord);
    }

    /**
     * isGameNotOver() - keeps game loop going if player has yet to reach number of max guesses or got the right answer <br>
     * @return True if player used the maximum number of guess or if player guessed the correct word. False otherwise
     */
    public boolean isGameNotOver(){

        if(getGuessesUsed()<getMaxGuesses()){
            return true;
        }
        else return getGuessesUsed() != getMaxGuesses();
    }

    /**
     * Normalizes user input according to the game rules:
     * - Trim leading/trailing whitespace
     * - Convert to UPPERCASE
     *
     * @param rawGuess, user's guess(perhaps be null)
     * @return normalized string (never null; may be empty)
     */
    public String normalize(String rawGuess) {
        if (rawGuess == null) {
            return "";
        }
        return rawGuess.trim().toUpperCase();
    }

    public static ConsoleUI.FeedbackType[] evaluateGuessAndGiveColoredFeedback(String playerGuess, String targetWord)
    {
        return WordRepo.GenerateColoredFeedback(playerGuess, targetWord);
    }
}
