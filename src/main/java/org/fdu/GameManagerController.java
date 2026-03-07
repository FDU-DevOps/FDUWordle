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
    /**
     * Constructs the controller to be used within this session
     * @param gameManager used to track the current session's gameManager constructor
     */
    public GameManagerController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // Opening page initializes a new game
    /** Generates a target word and sends it to the client
     * @return the selected target word for the current session
     */
    @GetMapping("/start-game")
    public String getTargetWord()
    {
        // Use WordRepo to pick a random target word and store it in the session
        String currentTargetWord = WordRepo.pickTargetWord();
        gameManager.setDebugTargetWord(currentTargetWord); // using this as it completes the same function needed in this case
        gameManager.setWon(false);      // make sure player did not win
        gameManager.resetGuessesUsed(); // reset used guesses at the start of the game
        // Send target word to client
        return gameManager.getTargetWord();
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