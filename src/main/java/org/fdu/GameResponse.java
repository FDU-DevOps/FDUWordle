package org.fdu;

/**
 * DTO GameResponse - stores game state
 */
public record GameResponse(String targetWord, String messageToPlayer, boolean hasWon) {}