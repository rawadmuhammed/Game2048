package pis.Game.Controller;

import pis.Game.Model.GameState;
import pis.Game.View.ViewInterface;
import pis.Game.Model.Model;

/**
 * Controller is a class that allows the communication between the Game logic
 * in the Module Class and the Game appearance in the View class
 *
 * @author Ward Jasim
 * @since 2.0
 * @version 2.1
 */
public class Controller implements ControllerInterface {
    private ViewInterface vI;
    private Model m;
    private GameState state;

    /**
     * @param vI takes an Interface so it's possible to give it an instance
     *           of different view class (for instance dark or light themes)
     *           that implements ViewInterface.
     */
   public Controller(ViewInterface vI){
        this.vI = vI;
        this.m = new Model();
        this.state = GameState.START_SCREEN;
    }
    public void playerInput(String direction){
       switch (state) {
          case START_SCREEN -> {   //nur das erste Mal, damit das Start screen beim aufruf von playerInput zum Spiel wechselt.
              state = GameState.IN_GAME;
          }
          case IN_GAME -> {
              m.play(direction);
          }
       }
   }
    public void refreshGame(){
       switch(state) {
           case START_SCREEN -> vI.startScreen();
           case IN_GAME -> {
               vI.show(m.grid);
               if (!m.game){
                   state = GameState.GAME_OVER;
               }
           }
           case GAME_OVER -> vI.gameOverScreen();
       }
    }
}
