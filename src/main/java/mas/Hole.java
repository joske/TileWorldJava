package mas;

public class Hole extends GridObject {

    public Hole(Location loc, int score, int to) {
        this.location = loc;
        this.score = score;
        this.timeout = to;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    public Tile getClosestTile() {
        return closest;
    }

    public void setClosestTile(Tile t) {
        closest = t;
    }
    
    public void update() {
        timeout--;
    }

    private int score;

    private int timeout;

    private Tile closest = null;
}
