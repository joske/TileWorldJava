package mas;

public interface HoleStrategy {
    Hole getBestHole(Grid grid, Location agent);
}
