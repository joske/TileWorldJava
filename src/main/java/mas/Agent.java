package mas;

import java.util.Random;

/*
 * Base class for the Agents. Strategies for finding 
 * Holes and Tiles and generating Paths are pluggable.
 * The Agents are StateMachines.
 */
public abstract class Agent extends GridObject {
    
    public static final int STATE_IDLE = 0;
    public static final int STATE_FIND_HOLE = 1;
    public static final int STATE_FIND_TILE = 2;
    public static final int STATE_MOVE_TO_TILE = 3;
    public static final int STATE_MOVE_TO_HOLE = 4;
    
    protected PathStrategy pathStrategy;
    protected HoleStrategy holeStrategy;
    protected TileStrategy tileStrategy;
    protected Grid grid;
    protected Random random = new Random();
    protected int state = STATE_IDLE;
    protected Hole hole = null;
    protected Tile tile = null;
    protected int score = 0;
    protected boolean gotTile = false;
    protected int id;

    public int getId() {
        return id;
    }
    
    public int getScore() {
        return score;        
    }
    
    public Grid getGrid(){
            return grid;
        }

    public void setGrid(Grid grid){
            this.grid = grid;
        }
        
    protected abstract void idle();
    protected abstract void findHole();
    protected abstract void findTile();
    protected abstract void moveToTile();
    protected abstract void moveToHole();
    
    /*
     * call appropriate method, depending on the 
     * current state
     */
    public void update() {
        switch (state) {
        case STATE_IDLE :
            idle();
            break;
        case STATE_FIND_HOLE :
            findHole();
            break;
        case STATE_FIND_TILE :
            findTile();
            break;
        case STATE_MOVE_TO_TILE :
            moveToTile();
        	break;
        case STATE_MOVE_TO_HOLE :
            moveToHole();
            break;            
        }
    }
    
    protected boolean move(Move m) {
        if (grid.possibleMove(location, m)) {
	        location.doMove(m);	        
	        return true;
        } else {                
            return false;
        }        
    }
    
    protected void pick() {        
        grid.pickTile(tile);
        gotTile = true;
    }
    
    protected void dump() {        
        score += grid.dumpTile(hole, tile);
        gotTile = false;
    }

    public boolean hasTile() {
        return gotTile;
    }

}
