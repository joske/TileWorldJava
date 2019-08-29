package mas;

/*
 * This class implements a simple strategy to move tiles into holes
 * It first searches the nearest Tile, moves to it (calculating a
 * new move every time-step), then looks for the nearest Hole,
 * and moves to that. Then throws the Tile in it. 
 */
public class DynamicAgent extends Agent {

    public DynamicAgent(Grid grid, Location loc, int id) {
        this.location = loc;
        this.grid = grid;
        this.id = id;
        this.holeStrategy = new ClosestHoleStrategy();
        this.pathStrategy = new SearchStrategy();
        this.tileStrategy = new ClosestTileStrategy();
    }

    protected void idle() {
        state = STATE_FIND_TILE;
    }
    
    protected void findHole() {
        hole = holeStrategy.getBestHole(grid, location);      
        if (hole != null) {
            state = STATE_MOVE_TO_HOLE;
        }
    }

    protected void findTile() {
        tile = tileStrategy.getBestTile(grid, location);        
        if (tile != null) {
            state = STATE_MOVE_TO_TILE;
        }
    }
    
    protected void moveToTile() {
        if (tile == null || !grid.isTile(tile.location)) {
            state = STATE_FIND_TILE;
        } else {
	        Move m = pathStrategy.getNextMove(grid, location, tile.location);
	        if (m != null) {
	            move(m);
		        if (location.distance(tile.location) == 1) {
		            pick();
		            state = STATE_FIND_HOLE;
		        }
	        }
        }
    }

    protected void moveToHole() {
        if (hole == null || !grid.isHole(hole.location)) {
            state = STATE_FIND_HOLE;
        } else {
	        Move m = pathStrategy.getNextMove(grid, location, hole.location);
	        if (m != null) {
	            move(m);
		        if (location.distance(hole.location) == 1) {
		            // dump tile, look for new hole
		            dump();
		            state = STATE_IDLE;
		        }
	        }
        }
    }

}
