package mas;

/*
 * represents an obstacle
 * @author jos
 */
public class Obstacle extends GridObject {

    public Obstacle(Location l) {
        this.location = l;
    }
    
    public Obstacle(int row, int col) {
        Location l = new Location(row, col);
        this.location = l;
    }

}
