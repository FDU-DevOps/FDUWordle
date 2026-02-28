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
    private final GameManager gameManager;
    public GameManagerController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // Opening page initializes a new game
    @GetMapping("/start-game")
    // Generate Target word (GET)
    // Send Target Word to Client
    public String getTargetWord()
    {
        // Use WordRepo to pick a random target word and store it in the session
        String currentTargetWord = WordRepo.pickTargetWord();
        gameManager.setDebugTargetWord(currentTargetWord); // using this as it completes the same function needed in this case
        // Send target word to client
        return gameManager.getTargetWord();
    }

    // Player Submitting Guess
    @PostMapping("/submit-guess")
    // Receive user guess (POST)
    // Check word and tell user if they won or not
    public GameResponse checkUserGuess(@RequestBody MessageData playerGuess)
    {
        // Check if word matches
        boolean won = gameManager.doesGuessMatch(playerGuess.playerGuess());
        String message = GameManager.gameStateMessage(won);

        // Return Result
        return new GameResponse(gameManager.getTargetWord(), message, won);
    }
}
