package mas;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Class that implements an optimal cost algorithm
 * for paths and local search with random moves
 * for next move
 * @author jos
 */
public class SearchStrategy implements PathStrategy {
        
    private static Random random = new Random();

    /**
     * This generates a path to the desired location
     * it uses a simple optimal cost algorithm
     * with manhattan-distance as extra heuristic
     */
    public Path generatePath(Grid grid, Location from, Location to) {
        List<Location> locationList = new ArrayList<>();
        TreeMap<Integer, List<Location>> queue = new TreeMap<>();
        locationList.add(from);
        queue.put(Integer.valueOf(0), locationList);
        while (!queue.isEmpty()) {
            Integer key = queue.firstKey();
            List<Location> path = queue.remove(key);
            Location last = path.get(path.size() - 1);
            if (last.equals(to)) {
                return createPath(grid, path);
            }
            generateNext(grid, to, path, Move.UP, queue);
            generateNext(grid, to, path, Move.LEFT, queue);
            generateNext(grid, to, path, Move.DOWN, queue);
            generateNext(grid, to, path, Move.RIGHT, queue);
        }
        return null;
    }
    
    /*
     * check for a loopy path
     */
    private boolean hasLoop(List<Location> l, Location nextLoc) {
        Iterator<Location> it = l.iterator();
        while(it.hasNext()) {
            Location loc = it.next();
            if (loc.equals(nextLoc)) {
                return true;
            }
        }
        return false;
    }
    
    /*
     * generate a new path in the specified direction.
     * If the path is allowed, it is added to the queue
     */
    private void generateNext(Grid grid, Location to, List<Location> path, int dir, TreeMap<Integer, List<Location>> queue) {
        Location last = (Location)path.get(path.size() - 1);
        Move m = new Move(dir);
        Location nextLoc = last.nextLocation(m);
        if (nextLoc.equals(to) || grid.possibleMove(last, m)) {
            /* this move is allowed, it leads to the goal,
             * or the way is clear */
            List<Location> newPath = new ArrayList<>(path);
            if (!hasLoop(newPath, nextLoc)) {
	            newPath.add(nextLoc);
	            /* calculate the cost + heuristic value */
	            int dd = newPath.size() + nextLoc.distance(to);
	            queue.put(Integer.valueOf(dd), newPath);
            }
        }       
    }
    
    /*
     * translate a list of Locations to a Path 
     * (a list of Moves)
     */
    private Path createPath(Grid grid, List<Location> l) {
        Path path = new Path();
        Iterator<Location> it = l.iterator();
        Location last = it.next();
        while (it.hasNext()) {
            Location loc = it.next();
            Move m = last.getMove(loc);
            path.addMove(m);
            last = loc;
        }
        return path;
    }

    /*
     * generate a path and return the first move
     */
    private Move getNextMoveFromPath(Grid grid, Location from, Location to) {
        Path path = generatePath(grid, from, to);
        Move m = null;
        if (path != null && path.getMoves().size() >= 1) {
            m = (Move)path.getMoves().remove(0);
        }
        return m;
    }
    
    /*
     * get a next move towards a goal. This returns 
     * a random move 20% of the time in order
     * to avoid local minima
     */
    public Move getNextMove(Grid grid, Location from, Location to) {
        Move m = null;
        float chance = random.nextFloat();
        if (chance > 0.80) {
            /* with 20% chance pick a move at random
             * instead of the normal move. This
             * avoids getting stuck in local minima
             */ 
            m = Move.randomMove();
            while (!grid.possibleMove(from, m)) {
                m = Move.randomMove();
            }
        } else {
            m = getNextLocalMove(grid, from, to);            
        }
        return m; 
    }
    
    /**
     * Use local search to get a next move
     */
    private Move getNextLocalMove(Grid grid, Location from, Location to) {
        int min_dist = Integer.MAX_VALUE;
        Move best_move = null;
        for (int dir = 1; dir <= 4; dir++) {            
	        Move m = new Move(dir);
	        Location l = from.nextLocation(m);
	        if (l.equals(to) || grid.possibleMove(from, m)) {
		        int dist = l.distance(to);
		        if (dist < min_dist) {
		            min_dist = dist;
		            best_move = m;
		        }
	        }
        }        
        return best_move;
    }
}
