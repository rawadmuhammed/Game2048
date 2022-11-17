package pis.Game.View;

/**
 * to use different user interfaces and themes, the controller must use this interface.
 *
 * @author Ward Jasim
 * @since 2.0
 * @version 2.1
 */
public interface ViewInterface {
    /**
     * draws the game field and the tiles in it if available.
     * @param grid takes the current grid form the module.
     */
    void show(int[] grid);

    /**
     * draws the Start screen of the Game
     */
    void startScreen();

    /**
     * draws the End Screen when the game is over
     */
    void gameOverScreen();

}
