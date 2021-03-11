package mas;

/*
 * This class plans more carefully its actions. It first 
 * locates the Hole which has the highest expected value,
 * the locates the tile closest to it, and the goes for 
 * that tile (calculating a full path in advance). After
 * it has the tile, it moves to the Hole it intended. If 
 * it is no longer there, it searches for the closest Hole. 
 */
public class PlanAheadAgent extends Agent {

    protected Path path = null;

    public PlanAheadAgent(Grid grid, Location loc, int id) {
        this.location = loc;
        this.grid = grid;
        this.id = id;
        this.holeStrategy = new SeuStrategy();
        this.pathStrategy = new AStarStrategy();
        this.tileStrategy = new ClosestTileStrategy();
    }

    protected void idle() {
        state = STATE_FIND_HOLE;
    }

    protected void findHole() {
        hole = holeStrategy.getBestHole(grid, location);      
        if (hole != null) {
            state = STATE_FIND_TILE;
        }
    }
    
    protected void findTile() {
        tile = tileStrategy.getBestTile(grid, hole.location);        
        if (tile != null) {
            state = STATE_MOVE_TO_TILE;
        }
    }
        
    protected void moveToTile() {
        if (tile == null || !grid.isTile(tile.location)) {
            state = STATE_FIND_TILE;
        } else {
	        if (path != null && path.getMoves().size() > 0) {
	            Move m = (Move) path.getMoves().remove(0);
	            if (!move(m)) {
	                // we haven't moved, find new path
	                path = null;                
	            }
		        if (location.distance(tile.location) == 1) {
		            pick();
		            state = STATE_MOVE_TO_HOLE;
		        }
	        } else {            
	            path = pathStrategy.generatePath(grid, location, tile.location);
	        }       
        }
    }

    protected void moveToHole() {        
        if (hole == null || !grid.isHole(hole.location)) {
            /* where did our hole go? */
            hole = grid.getClosestHole(location);    
            if (hole != null) {
	            path = pathStrategy.generatePath(grid, location, hole.location);
            }
        } else {
	        if (path != null && path.getMoves().size() > 0) {
	            Move m = (Move) path.getMoves().remove(0);
	            if (!move(m)) {
	                // we haven't moved, find new path
	                path = null;
	            }
		        if (location.distance(hole.location) == 1) {
		            // dump tile, look for new hole
		            dump();
		            state = STATE_IDLE;
		        }
	        } else {            
	            path = pathStrategy.generatePath(grid, location, hole.location);
	        }
        }
    }
    
}
