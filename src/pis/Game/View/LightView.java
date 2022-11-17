package pis.Game.View;

import pis.Game.Controller.ControllerInterface;
import processing.core.PApplet;
import pis.Game.Controller.Controller;
import processing.core.PImage;

import java.lang.Math;

/**
 * LightView displays the Game in a Light theme that is easy to be seen
 * in a daylight environments.
 *
 *  @author Ward Jasim
 *  @since 2.0
 *  @version 2.1
 */
public class LightView extends PApplet implements ViewInterface{
    public static void main(String[] args) {PApplet.main(LightView.class);}

    /**
     * the beginning of the Game field in x direction
     */
    final int X_POS = 0;
    /**
     * the beginning of the Game field in y direction
     */
    final int Y_POS = 0;
    /**
     * the size of the Edge of the Field in x direction
     */
    final int X_OFFSET = 20;
    /**
     * the size of the Edge of the Field in y direction
     */
    final int Y_OFFSET = 20;
    /**
     * the size of a Tile
     */
    final int SIZE_TILE = 80;
    /**
     * the size of the border of the Tiles
     */
    final int SIZE_BORDER = 10;
    /**
     * the size of the Game screen in x direction
     */
    final int X_SIZE = 2*X_POS+2*X_OFFSET+SIZE_BORDER+4*(SIZE_TILE+SIZE_BORDER);
    /**
     * the size of the Game screen in y direction
     */
    final int Y_SIZE  = 2*Y_POS+2*Y_OFFSET+SIZE_BORDER+4*(SIZE_TILE+SIZE_BORDER);
    ControllerInterface cI;
    /**
     * the start image for the light mode screen.
     */
    PImage startLogo;

    public LightView(){setSize(X_SIZE, Y_SIZE);}
    public void setup() {
        cI = new Controller(this);
        startLogo = loadImage("startLogoLight.png");
        textAlign(CENTER, CENTER);
        textSize(27);
        noStroke();
        background(color(179, 189, 214));
        colorMode(HSB, 360, 100, 100);
        print("X_SIZE:", X_SIZE, "Y_SIZE:", Y_SIZE);
    }
    /**
     * brings the game state from the module via the controller and refreshes
     * and draws the Game 60 time per second
     */
    public void draw() {cI.refreshGame();}
    public void show(int[] grid) {
        int edge_length = (int)(Math.sqrt(grid.length));
        int i = 0;
        int X, Y;
        background(350);
        for (int y=0; y<edge_length; y++) {
            Y = Y_POS+Y_OFFSET+SIZE_BORDER+y*(SIZE_TILE+SIZE_BORDER);
            for (int x=0; x<edge_length; x++) {
                X = X_POS+X_OFFSET+SIZE_BORDER+x*(SIZE_TILE+SIZE_BORDER);
                // fill(color(179, 189, 214));
                fill(color(30+log(grid[i]+1)/log(2)*10, 100, 100));
                rect(X, Y, SIZE_TILE, SIZE_TILE, 15);
                if (grid[i] != 0) {
                    fill(color(271, 0, 1));
                    text(grid[i], X+SIZE_TILE/2+1, Y+SIZE_TILE/2+1);
                }
                i++;
            }
        }
    }
    public void startScreen(){
        startLogo.resize(width, height);
        background(startLogo);
        textSize(20);
        fill(360, 100, 100);
        text("Pfeiltaste zum Starten", width/2, 30);
    }
    public void gameOverScreen(){
            background(360, 100, 100);
            textSize(22);
            text("GAME OVER", width/2, height/2);
    }
    /**
     * takes the input of the user if it is an arraw key and
     * convert it to String.
     */
    public void keyPressed() {
        String k;
        if(key == CODED) {
            k = switch (keyCode) {
                case LEFT -> "L";
                case RIGHT -> "R";
                case UP -> "U";
                case DOWN -> "D";
                default -> "";
            };
            cI.playerInput(k);
        }
    }
}

/* Game 2048 by @denkspuren, 2014-09-11, 11:47
   New BSD License: http://opensource.org/licenses/BSD-3-Clause

   This is an implementation of 2048 without animation effects.

   Use arrow keys (left, right, up and down) to move tiles on the
   grid. Game ends if the tiles cannot be moved anymore.

   For the game and its history see e.g. Wikipedia:
   http://en.wikipedia.org/wiki/2048_(video_game)
   http://de.wikipedia.org/wiki/2048_(Computerspiel)
*/
