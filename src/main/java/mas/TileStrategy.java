package mas;

/*
 * Interface for the strategy to find a Tile
 * @author jos
 */
public interface TileStrategy {
    Tile getBestTile(Grid grid, Location loc);
}
