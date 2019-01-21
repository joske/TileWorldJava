package mas;

/*
 * Interface for finding a path to the goal, or the next
 * move towards the goal
 * @author jos
 */
public interface PathStrategy {
    public Path generatePath(Grid grid, Location from, Location to);
    public Move getNextMove(Grid grid, Location from, Location to);
}
