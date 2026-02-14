package org.fdu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static org.fdu.GameManager.*;

class GameManagerTest {

    @Test
    @DisplayName("Compare that player guess and target word match")
    void isWinningGuessTest() {
        String playerGuessTest = "OCEAN";
        String targetWordMatches = "OCEAN";
        String targetWordDoesNotMatch = "BLOOM";
        String targetWordIsAnagram = "CANOE";

        // Words match -- Happy Path Case (returns true)
        assertTrue(isWinningGuess(playerGuessTest,targetWordMatches), "Word match not handled correctly");

        // Words do not match -- should return false
        assertFalse(isWinningGuess(playerGuessTest,targetWordDoesNotMatch), "Word mismatch not handled correctly");

        // Edges and Corner Cases
        // Anagrams should return false
        assertFalse(isWinningGuess(playerGuessTest,targetWordIsAnagram), "Anagrams not handled correctly");
    }
}