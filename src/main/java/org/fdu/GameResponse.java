package org.fdu;

/**
 * DTO GameResponse - stores game state and returns to the client <br>
 * @param targetWord stores the current target word for the session
 * @param hasWon stores whether the user has won the game or not
 * @param guessesUsed stores how many guesses the user used
 * @param isValidGuess flag to store if the guess that user entered is valid
 * @param feedbackColors sting array to store type of color of the feedback
 */
public record GameResponse(String targetWord, boolean hasWon, int guessesUsed, boolean isValidGuess,String[] feedbackColors) {}