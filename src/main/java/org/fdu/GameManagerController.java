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
    private String currentTargetWord;

    // Opening page initializes a new game
    @GetMapping("/start-game")
    // Generate Target word (GET)
    // Send Target Word to Client
    public String getTargetWord()
    {
        // Use WordRepo to pick a random target word and store it in the session
        currentTargetWord = WordRepo.pickTargetWord();

        // Send target word to client
        return currentTargetWord;
    }

    // Player Submitting Guess
    @PostMapping("/submit-guess")
    // Receive user guess (POST)
    // Check word and tell user if they won or not
    public GameResponse checkUserGuess(@RequestBody MessageData playerGuess)
    {
        // Check if word matches
        String message = "";
        boolean won = playerGuess.playerGuess().equalsIgnoreCase(currentTargetWord);
        if(won)
        {
            message = "User wins!";
        }
        else {
            message = "User loses!";
        }
        // Return Result
        return new GameResponse(currentTargetWord, message, won);
    }
}
