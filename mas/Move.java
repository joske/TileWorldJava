package mas;

import java.util.Random;

/*
 * represents a possible Move (can be UP, DOWN, LEFT or RIGHT)
 * @author jos
 */
public class Move {
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    public int direction;

    private static Random random = new Random();
    
    public Move(int dir) {
        direction = dir;
    }
    
    /*
     * return a random move
     */
    public static Move randomMove() {
        /* +1 since nextInt returns 0-3 */
        return new Move(random.nextInt(4) + 1);
    }
    
    public String toString() {
        if (direction == UP) {
            return "UP";
        } else if (direction == DOWN) {
            return "DOWN";            
        } else if (direction == LEFT) {
            return "LEFT";            
        } else if (direction == RIGHT) {
            return "RIGHT";            
        }
        return "UNKNOWN MOVE!";
    }
}
