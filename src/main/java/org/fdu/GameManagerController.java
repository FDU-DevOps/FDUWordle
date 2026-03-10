package org.fdu;

import org.springframework.web.bind.annotation.*;

/**
 * Controller manages a Wordle Game with guesses <br>
 * GetMapping("/start-game")    - Handles Logic once the game starts (choosing target word)
 * PostMapping("/submit-guess") - Handles player guessing a word and telling player if they won
 */
@RestController
@RequestMapping("/api/FDUWordle")
public class GameManagerController
{

    // Stores target word for this session of Wordle
    private GameManager gameManager;
    private GameResponse gameState;
    public GameManagerController(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    // Opening page initializes a new game
    /** Generates a target word and sends it to the client
     * @return the selected target word for the current session
     */
    @PostMapping("/start-game")
    public GameResponse getTargetWord()
    {
        gameState = gameManager.startGame();
        return gameState;
    }
    // Player Submitting Guess

    /**
     * Checks whether the player's guess compared to the target word and responds accordingly
     * @param playerGuess the passed player guess from the client
     * @return GameResponse DTO that stores the target word, message to the user, and whether the user won or not
     */
    @PostMapping("/submit-guess")
    public GameResponse checkUserGuess(@RequestBody MessageData playerGuess)
    {
        return gameManager.submitGuess(playerGuess);
    }
}