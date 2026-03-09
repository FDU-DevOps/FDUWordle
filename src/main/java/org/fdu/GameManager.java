package org.fdu;

import org.springframework.stereotype.Service;
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
@Service
public class GameManager {

    /** Max number of guesses allowed in a Wordle game */
    public static final int MAX_GUESSES = 6;
    private String targetWord;
    private int guessesUsed = 0;
    private boolean hasWon = false;


    /**
     * GameManager() - Initializes the targetWord from the WordRepo Class method pickTargetWord() <br>
     * targetWord - (String) the correct answer to the game is picked and assigned to targetWord
     * Initializes and loads the dictionary as well before picking a target word
     */
    public GameManager() {
        try {
            // Load Dictionary before picking a word
            if (WordRepo.getWords().isEmpty()) {
                WordRepo.loadDictionary("dictionary.csv");
            }
            this.targetWord = WordRepo.pickTargetWord();
        } catch (Exception e) {
            // Fallback default word so the app doesn't crash if the file is missing
            this.targetWord = "DEVIL";
        }
    }

    /**
     * getWon() - Returns whether the player has won the game <br>
     * @return won - (boolean) true if the player has won, false otherwise
     */
    public boolean getWon(){
        return hasWon;
    }

    /**
     * setWon() - Sets the won status of the game <br>
     * @param won - (boolean) true if the player has won, false otherwise
     */
    public void setWon(boolean won){
        hasWon = won;
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
     * resetGuessesUsed() - Resets the number of guesses used back to 0 <br>
     * Called when a new game session is started
     */
    public void resetGuessesUsed(){
        guessesUsed = 0;
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
        hasWon = normalizedGuess.equals(targetWord);
        return hasWon;
    }

    /**
     * isGameNotOver() - keeps game loop going if player has yet to reach number of max guesses or got the right answer <br>
     * @return True if player used the maximum number of guess or if player guessed the correct word. False otherwise
     */
    public boolean isGameNotOver() {
            return getGuessesUsed()< MAX_GUESSES;
        }

    /**
     * getNormalizedGuess(String rawGuess) - Retrieves the normalized guess from the WordRepo Class
     * @param rawGuess, player's guess(perhaps be null)
     * @return normalized string from WordRepo Class
     */

    public String getNormalizedGuess(String rawGuess){
        return WordRepo.normalize(rawGuess);
    }

    /**
     * Takes in playerGuess and targetWord and generates colored feedback based on the two
     * @param playerGuess the player's guess for what the target word is
     * @param targetWord the generated target word for this instance of Wordle
     * @return player guess with color coded feedback based on the target word
     */
    public static WordRepo.FeedbackType[] evaluateGuessAndGiveColoredFeedback(String playerGuess, String targetWord)
    {
        return WordRepo.GenerateColoredFeedback(playerGuess, targetWord);
    }

    /**
     * Checks if the user has won or lost and outputs text correlating to the game state <br>
     * Scope: Currently programmed to handle 1 case (player has won or player has lost - REFACTOR for future uses)
     * @param hasWon - indicates if the player has won or lost the game
     * @return message indicating whether the player has won or lost the game
     */

    public static String gameStateMessage(boolean hasWon)
    {
        return hasWon ? "CORRECT! YOU GUESSED THE WORD: " : "YOU LOST! THE CORRECT ANSWER WAS:";
    }

    /**
     * Displays the introduction messages for the game.
     * @param manager instance of the GameManager class, used to display max guesses
     */
    public static void showIntro (GameManager manager){
        ConsoleUI.println("WELCOME TO WORDLE! GUESS THE SECRET WORD.");
        ConsoleUI.println("YOU HAVE " + manager.getMaxGuesses() + " GUESSES.");
    }
    /**
     Overrides the randomly selected target word with a tester-defined word in debug mode. <br>
     <p>
     Scope: <br>
     Used only when the SECRET_WORD debug command is activated. <br>
     Allows testers to manually set the target word for testing edge cases. <br>
     Does not affect normal gameplay when debug mode is not used. <br>
     </p>
     @param debugWord the custom target word entered by the tester
     @author Emirlan Asanakunov
     */
    public void setDebugTargetWord(String debugWord){
        this.targetWord = debugWord;
    }

    /**
     Determines whether the player input activates debug mode. <br>
     <p>
     Scope: <br>
     Checks if the normalized user input matches the special debug command (SECRET_WORD). <br>
     Ensures debug mode can only be activated before any guesses have been used. <br>
     Prevents testers from changing the target word after gameplay has started. <br>
     </p>
     @param guess normalized player input
     @return true if the debug command is detected and no guesses have been used; false otherwise
     @author Emirlan Asanakunov
     */
    public boolean isDebugCommand(String guess){
        return guess.equals("SECRET_WORD") && guessesUsed == 0;
    }

    /**
     submitGuess(String rawGuess) - Processes a player's raw guess through the full game flow. <br>
     <p>
     Flow: <br>
     1. Normalizes the raw guess via getNormalizedGuess() <br>
     2. Validates the normalized guess with WordRepo.isInvalidGuess() <br>
     3. If valid: processes the guess, generates colored feedback, and builds a game response <br>
     4. If invalid: returns early with an invalid guess message and no feedback <br>
     </p>
     @param rawGuess (String) the player's raw guess
     @return GameResponse containing: <br>
     targetWord: the current secret word <br>
     message: game state message (win/loss/invalid) <br>
     hasWon: whether the player has won or no <br>
     guessesUsed: number of guesses made <br>
     isValidGuess: false if guess was invalid, true otherwise <br>
     feedbackColors: String[] of color feedback (e.g. "GREEN", "YELLOW", "GRAY")
     */
    public GameResponse submitGuess(MessageData rawGuess){
        String normalized = getNormalizedGuess(rawGuess.playerGuess()); //Normalize guess

        if (WordRepo.isInvalidGuess(normalized)) { //if guess is invalid
            return new GameResponse(
                    getTargetWord(),
                    "Invalid guess. Must be exactly 5 letters (A–Z).",
                    false,
                    getGuessesUsed(),
                    false,
                    null
            );
        }
        doesGuessMatch(normalized);

        WordRepo.FeedbackType[] feedbackColors =
                evaluateGuessAndGiveColoredFeedback(normalized, getTargetWord()); //Get the colored feedback as Enum

        String[] stringFeedbackColors = new String[feedbackColors.length];  //Convert Enum to the string
        for (int i = 0; i < feedbackColors.length; i++) {
            stringFeedbackColors[i] = feedbackColors[i].name();
        }

        return new GameResponse(
                getTargetWord(),
                gameStateMessage(hasWon),
                hasWon,
                getGuessesUsed(),
                true,
                stringFeedbackColors
        );
    }

}
