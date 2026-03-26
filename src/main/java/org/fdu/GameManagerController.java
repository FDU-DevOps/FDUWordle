package org.fdu;

import jakarta.servlet.http.HttpSession;
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

    /** Generates a target word, stores a fresh GameManager in the session and returns the initial game state
     * @param session HttpSession to store the GameManger instance per browser session
     * @return GameResponse DTO with initial game state
     */
    @PostMapping("/start-game")
    public GameResponse startGame(HttpSession session)
    {
       GameManager gameManager = new GameManager();
       session.setAttribute("gameManager", gameManager);
       return gameManager.startGame();
    }

    /**
     * Checks whether the player's guess compared to the target word and responds accordingly
     * @param playerGuess the passed player guess from the client
     * @return GameResponse DTO that stores the target word, message to the user, and whether the user won or not
     */
    @PostMapping("/submit-guess")
    public GameResponse submitGuess(@RequestBody MessageData playerGuess, HttpSession session)
    {
        GameManager gameManager = (GameManager) session.getAttribute("gameManager");
        if(gameManager == null){
            GameManager newGameManager = new GameManager();
            session.setAttribute("gameManager", newGameManager);
            return newGameManager.startGame();
        }
        return gameManager.submitGuess(playerGuess);
    }
}