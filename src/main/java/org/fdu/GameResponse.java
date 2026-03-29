package org.fdu;

/**
 * DTO GameResponse - stores game state and returns to the client <br>
 * @param targetWord stores the current target word for the session
 * @param guessesRemaining stores how many guesses the user has remaining
 * @param maxGuesses stores the max amount of guesses for a wordle game
 * @param wordLength stores the length of the target word
 * @param hasWon stores whether the user has won the game or not
 * @param isGameOver stores whether the game is over or not
 * @param isValidGuess flag to store if the guess that user entered is valid
 * @param previousGuess stores the most recent valid guess
 * @param feedbackColors sting array to store type of color of the feedback
 */
public record GameResponse(
        String targetWord,
        int guessesRemaining,
        int maxGuesses,
        int wordLength,
        boolean hasWon,
        boolean isGameOver,
        boolean isValidGuess,
        boolean isNotInWordList,
        MessageData previousGuess,
        String[] feedbackColors
){}
