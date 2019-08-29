package mas;

/*
 * strategy that looks for closest hole to given location
 */
public class ClosestHoleStrategy implements HoleStrategy {

    public Hole getBestHole(Grid grid, Location agent) {
        return grid.getClosestHole(agent);
    }

}
