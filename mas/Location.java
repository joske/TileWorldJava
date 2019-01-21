package mas;

import java.util.Random;

/*
 * represents a location on the grid
 * @author jos
 */
public class Location {
    
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public void doMove(Move m) {
        switch (m.direction) {
    	case Move.UP:
    	    row--;
    	    break;
    	case Move.DOWN:
    	    row++;
    	    break;
    	case Move.LEFT:
    	    col--;
    	    break;
    	case Move.RIGHT:
    	    col++;
    	    break;
    	default: 
    	    // unknown direction
        }        
    }
    
    public Location nextLocation(Move m) {
        int nr = row;
        int nc = col;
        
        switch (m.direction) {
    	case Move.UP:
    	    nr--;
    	    break;
    	case Move.DOWN:
    	    nr++;
    	    break;
    	case Move.LEFT:
    	    nc--;
    	    break;
    	case Move.RIGHT:
    	    nc++;
    	    break;
    	default: 
    	    // unknown direction
        }    
        return new Location(nr, nc);
    }
    
    public static Location randomLocation(int rows, int cols) {
        int r = random.nextInt(rows);
        int c = random.nextInt(cols);
        Location location = new Location(r, c);
        return location;
    }
    
    public int distance(Location other) {
        return Math.abs(row - other.row) + Math.abs(col - other.col);
    }
    
    public boolean equals(Object o) {
        if (o instanceof Location) {
            Location other = (Location)o;
            if (row == other.row && col == other.col) {
                return true;
            }
        }
        return false;
    }
    
    public Move getMove(Location newLoc) {
        if (row == newLoc.row) {
            if (col == newLoc.col + 1) {
                return new Move(Move.LEFT);
            } else {
                return new Move(Move.RIGHT);
            }
        } else {
            if (row == newLoc.row + 1) {
                return new Move(Move.UP);
            } else {
                return new Move(Move.DOWN);
            }
        }
    }
    
    public String toString() {
        return "" + row + " - " + col;
    }

    private static Random random = new Random();
    public int row;
    public int col;
}
