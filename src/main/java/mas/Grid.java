package mas;

import java.util.*;

/*
 * This class implements the grid Model, it knows all the objects 
 * it contains, and asks them to update, if the grid has to update.
 * It also contains methods for locating the nearest Tile and Hole.
 */
public class Grid {
    
    private static final int MAX_LIFETIME = 100;
    private static final int MAX_SCORE = 9;

    private int numAgents;
    private Random random = new Random();
    private GridObject[][] objects;
    private int rows;
    private int cols;
    private List<Obstacle> obstacles = new ArrayList<>();
    private List<Hole> holes = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();
    private List<Agent> agents = new ArrayList<>();
    private GridView view;

    public Grid(int rows, int cols, int obstacleCount, int tileCount, 
                int holeCount, int agentCount) {
        this.rows = rows;
        this.cols = cols;        
        this.numAgents = agentCount;
        objects = new GridObject[rows][cols];
        // create obstacles
        for (int i = 0; i < obstacleCount; i++) {
            createObstacle();
        }
        // create holes
        for (int i = 0; i < holeCount; i++) {
            createHole();
        }
        // create tiles
        for (int i = 0; i < tileCount; i++) {
            createTile();
        }
        //create the agents
        for (int i = 1; i <= numAgents; i++) {
            Agent a;
            Location loc = randomFreeLocation();
           a = new PlanAheadAgent(this, loc, i);
            // if (i%2 == 0) {
            //     a = new DynamicAgent(this, loc, i);
            // } else {
            //     a = new PlanAheadAgent(this, loc, i);
            // }
            a.setGrid(this);
            agents.add(a);
            objects[loc.row][loc.col] = a;
        }
        
    }
    
    public void start() {
        view = new GraphicalGridView(this);
        //view = new TextGridView(this);
        view.getController().startGrid();        
    }
    
    private Obstacle createObstacle() {
        Location loc = randomFreeLocation();
        Obstacle o = new Obstacle(loc);
        objects[loc.row][loc.col] = o;
        obstacles.add(o);
        return o;
    }
    
    private boolean allowedLocation(Location loc) {
        int row = loc.row;
        int col = loc.col;
        
        if (row >= rows || row < 0) {
            return false;
        }
        if (col < 0 || col >= cols) {
            return false;
        }     
        return true;
    }
    
    private Location randomFreeLocation() {
        Location loc = Location.randomLocation(rows, cols);;
        while (!allowedLocation(loc) || objects[loc.row][loc.col] != null) {
            loc = Location.randomLocation(rows, cols);
        }
        return loc;
    }
    
    private Tile createTile() {
        Location loc = randomFreeLocation();
        Tile t = new Tile(loc);
        objects[loc.row][loc.col] = t;
        tiles.add(t);
        return t;
    }
    
    private void removeTile(Tile t) {        
        objects[t.location.row][t.location.col] = null;
        tiles.remove(t);
    }
    
    private Hole createHole() {
        Location loc = randomFreeLocation();
        int score = random.nextInt(MAX_SCORE);
        int to = random.nextInt(MAX_LIFETIME);
        Hole h = new Hole(loc, score, to);
        objects[loc.row][loc.col] = h;
        holes.add(h);
        return h;        
    }
    
    public int getRows(){
            return rows;
        }

    public int getCols(){
            return cols;
        }

    public boolean isHole(Location loc) {
        Object o = objects[loc.row][loc.col];
        return o instanceof Hole;
    }

    public boolean isTile(Location loc) {
        Object o = objects[loc.row][loc.col];
        return o instanceof Tile;
    }

    public boolean possibleMove(Location from, Move m) {        
        Location newloc = from.nextLocation(m);
        int row = newloc.row;
        int col = newloc.col;
        
        if (!allowedLocation(newloc)) {
            return false;
        }
        Object o = objects[row][col];
        if (o != null) {
            return false;
        }
        return true;
    }
    
    public Tile getClosestTile(Location l) {
        int minDist = Integer.MAX_VALUE;
        Tile best = null;
        
        Iterator<Tile> it = getTiles().iterator();
        while (it.hasNext()) {
            Tile t = it.next();
            if (isTile(t.location)) {
	            int dist = t.location.distance(l);
	            if (dist < minDist) {
	                minDist = dist;
	                best = t;
	            }
            } else {
                it.remove();
            }
        }
        return best;
    }
    
    public Hole getClosestHole(Location l) {
        int minDist = Integer.MAX_VALUE;
        Hole best = null;
        
        Iterator<Hole> it = getHoles().iterator();
        while (it.hasNext()) {
            Hole h = it.next();
            int dist = h.location.distance(l);
            if (dist < minDist) {
                minDist = dist;
                best = h;
            }
        }
        return best;
    }
    
    /*
     * update all the objects in the grid
     */
    public void update() {
        // update holes
        Iterator<Hole> i = holes.iterator();
        while (i.hasNext()) {
            Hole h = i.next();
            h.update();
            if (h.getTimeout() < 1) {
                // hole is expired, remove
                i.remove();
                objects[h.location.row][h.location.col] = null;
            }
        }
        /* 50% chance to create a new Hole */
        if (random.nextBoolean()) {
	        Hole newhole = createHole();
	        holes.add(newhole);
        }
        // update the agents
        Iterator<Agent> i2 = agents.iterator();
        while (i2.hasNext()) {
            Agent a = i2.next();
            Location loc = a.getLocation();
            objects[loc.row][loc.col] = null;
            a.update();
            loc = a.getLocation();
            objects[loc.row][loc.col] = a;
        }
        view.gridChanged();
    }
    
    public void pickTile(Tile t) {
        removeTile(t);
    }
    
    public int dumpTile(Hole h, Tile t) {
        h.setTimeout(1); /* this will make the hole disappear */
        /* create new tile */
        createTile(); 
        return h.getScore();
    }

    public List<Agent> getAgents(){
        return agents;
    }

    /* unit test only */    
    void setObject(GridObject o, int row, int col) {
        objects[row][col] = o;
    }

    public GridObject getObjectAt(int row, int col) {
        return objects[row][col];
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

}
