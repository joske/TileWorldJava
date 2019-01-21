package mas;

/*
 * Strategy that looks for closest tile to a given location
 */
public class ClosestTileStrategy implements TileStrategy {
    
    public Tile getBestTile(Grid grid, Location loc) {
        return grid.getClosestTile(loc);
    }

}
