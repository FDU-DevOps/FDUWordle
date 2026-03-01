package org.fdu;

/**
 * DTO GameResponse - stores game state and returns to the client <br>
 * @param targetWord stores the current target word for the session
 * @param messageToPlayer stores the message to be output to the player regarding their game state (won/loss,etc.)
 * @param hasWon stores whether the user has won the game or not
 */
public record GameResponse(String targetWord, String messageToPlayer, boolean hasWon) {}