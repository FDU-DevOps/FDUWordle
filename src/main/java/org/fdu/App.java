package org.fdu;

/**
 * Contains main() and creates/starts the game components
 */
public class App 
{
    public static void main( String[] args) {
        GameManager manager = new GameManager();
        manager.runGame();
    }
}