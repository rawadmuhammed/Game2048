package pis.Game.Controller;

/**
 * the ControllerInterface tells the Controller which methods must be implemented.
 *
 * @author Ward Jasim
 * @since 2.0
 * @version 2.1
 */

public interface ControllerInterface {
    /**
     * records the chosen direction that the player swaps into.
     * @param direction input of an direction key as a String.
     * @since 2.0
     */
    void playerInput(String direction);
    /**
     * query the new Game information after a move was calculated by the module.
     * @since 2.0
     */
    void refreshGame();
}
