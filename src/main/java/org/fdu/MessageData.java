package org.fdu;

/**
 * DTO MessageData - stores what the player is guessing<br>
 * @param playerGuess stores the player's guess for this Wordle session
 */
public record MessageData(String playerGuess) {}