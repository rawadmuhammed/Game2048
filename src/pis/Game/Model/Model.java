package pis.Game.Model;

import java.util.Arrays;
import java.util.Random;

import static processing.core.PApplet.arrayCopy;

/**
 * the Model is a class that contains all the Game logic.
 * this class receives information via the controller from the Userinput
 * and plays a move. the grid provides the new game state and can be displayed
 * using the View.
 *
 *  @author Ward Jasim
 *  @since 2.0
 *  @version 2.1
 */
public class Model {
    /**
     * saves the current Game progress as an array and allows for changes and manipulation
     */
    public int[] grid;
    /**
     * saves the current score of the Game in points
     */
    int score;
    /**
     * tells if is still in Game or the Game is over.
     */
    public boolean game;
    Random random;
    public Model(){
        this.grid = new int [16]; // default values are 0
        this.score = 0;
        this.game = true;
        random = new Random();
        start();
    }

    /**
     * calculates the number of free slots in the Game.
     * @param grid takes the game as an array of numbers
     * @return the number of free slots in the Game.
     */
    public int free_slots(int[] grid) {
        int i = 0;
        for (int val : grid) {
            if (val == 0) i++;
        }
        return i;
    }

    /**
     * plays tow random moves for the beginning of the Game.
     * @since 2.1
     */
    void start(){
            random_tile(grid);
            random_tile(grid);
    }

    /**
     * rotates the game fields by 90 degree, so that the Game can be
     * played in all four directions.
     * @param grid takes the Game as an array.
     */
    public void rotate(int[] grid) {
        int[] temp_grid = new int[grid.length];
        for (int i=0; i<grid.length; i++) {
            temp_grid[i+12-(i%4)*5-(i/4)*3] = grid[i];
        }
        arrayCopy(temp_grid, grid);
    }

    /**
     * rotates the Game by 90° for more than one times if needed
     * @param grid the Field of Game as an array
     * @param n the Number of times it should rotate the Game by 90°.
     */
    public void rotate(int[] grid, int n) {
        for (int i=1; i<=(n%4); i++) { rotate(grid); }
    }

    /**
     * shifts the whole field to the swap direction after a move was done.
     * @param grid its elements get shifted to the empty fields of the swap direction.
     */
    void shift(int[] grid) {
        int offset=0;
        for (int i=0; i<grid.length; i++) {
            if (i%4 == 0) {
                offset = 0;
            }
            if (grid[i] == 0) {
                offset++;
            } else if (offset > 0) {
                grid[i-offset] = grid[i];
                grid[i] = 0;
            }
        }
    }

    /**
     * plays a move (only to the left side of the field)
     * @param grid a rotated Game Field should be given
     * @return the new Game score
     */
    int move(int[] grid) {
        int score = 0;
        shift(grid);
        score = merge(grid);
        shift(grid);
        return score;
    }

    /**
     * calculates if the Game is over
     * @param grid for the calculation on
     * @return true if the Game is over and false if the Game not over.
     */
    public boolean is_game_over(int[] grid) {
        int[] temp_grid = new int[grid.length];
        arrayCopy(grid, temp_grid);
        for (int i=1; i<=4; i++) {
            move(temp_grid); rotate(temp_grid);
        }
        return Arrays.equals(temp_grid, grid);
    }

    /**
     * merges two fields that have the same value after the field is shifted
     * in its direction.
     * @param grid a field after it's been shifted
     * @return the new Game score after the merging.
     */
    int merge(int[] grid) {
        int score = 0;
        for (int i=0; i<grid.length; i++) {
            if (i%4 < 3) {
                if (grid[i] > 0 && grid[i] == grid[i+1]) {
                    grid[i] += grid[i+1];
                    grid[i+1] = 0;
                    score += grid[i];
                }
            }
        }
        return score;
    }

    /**
     * inserts a random tile on a random empty filed
     * @param grid the Game Field
     * @param n the Number of free slots
     * @param val random number of the Value 2 or 4
     */
    void insert_tile(int[] grid, int n, int val) {
        for (int i=0; i<grid.length; i++) {
            if (grid[i] == 0) {
                if (n == 0) {
                    grid[i] = val;
                    break;
                }
                n--;
            }
        }
    }

    /**
     * generates a random Tile and inserts it to an empty field
     * @param grid the Game Field
     */
    public void random_tile(int[] grid) {
        int pos, val;
        pos = random.nextInt(free_slots(grid));
        val = random.nextFloat(0,1) < 0.9 ? 2 : 4;
        insert_tile(grid, pos, val);
    }

    /**
     * plays the Game in a given direction
     * @param direction takes a direction a String and rotates the Game Field based on it.
     */
    public void play(String direction){
        int[] temp_grid = new int[grid.length];
        arrayCopy(grid, temp_grid);
        if (game) {
            System.out.println("it plays");
            switch (direction) {
                case "L" -> {
                    score += move(grid);
                    System.out.println("it is left");
                }
                case "R" -> {
                    rotate(grid, 2);
                    score += move(grid);
                    rotate(grid, 2);
                    System.out.println("it is right");
                }
                case "U" -> {
                    rotate(grid);
                    score += move(grid);
                    rotate(grid, 3);
                    System.out.println("it is up");
                }
                case "D" -> {
                    rotate(grid, 3);
                    score += move(grid);
                    rotate(grid);
                    System.out.println("it is down");
                }
            }
        }
        if (!Arrays.equals(grid, temp_grid)) {
            random_tile(grid);
            System.out.println("SCORE = " + score);
        }
        if (is_game_over(grid)==true) {
            game = false;
            System.out.println("GAME OVER. YOUR SCORE =" + score);
        }
    }

}

/*  0  1  2  3     3  7 11 15     +12 +7  +2  -3  -> -5
    4  5  6  7     2  6 10 14     +9  +4  -1  -6  -> -5
    8  9 10 11     1  5  9 13     +6  +1  -4  -9  -> -5
   12 13 14 15     0  4  8 12     +3  -2  -7  -12 -> -5
                                   |   |   |   |
                                   V   V   V   V
                                  -3  -3  -3  -3
 */