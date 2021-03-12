package mas;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Class that implements an optimal cost algorithm
 * for paths and local search with random locations
 * for next move
 * @author jos
 */
public class SearchStrategy implements PathStrategy {

    private static Random random = new Random();

    /**
     * This generates a path to the desired location it uses a simple optimal cost
     * algorithm with manhattan-distance as extra heuristic
     */
    public Path generatePath(Grid grid, Location from, Location to) {
        Locations locationList = new Locations(from, 0);
        PriorityQueue<Locations> queue = new PriorityQueue<>();
        queue.add(locationList);
        while (!queue.isEmpty()) {
            Locations path = queue.poll();
            Location last = path.getLast();
            if (last.equals(to)) {
                return path.createPath();
            }
            generateNext(grid, to, path, Move.UP, queue);
            generateNext(grid, to, path, Move.LEFT, queue);
            generateNext(grid, to, path, Move.DOWN, queue);
            generateNext(grid, to, path, Move.RIGHT, queue);
        }
        return null;
    }

    /*
     * generate a new path in the specified direction. If the path is allowed, it is
     * added to the queue
     */
    private void generateNext(Grid grid, Location to, Locations path, int dir, PriorityQueue<Locations> queue) {
        Location last = (Location) path.getLast();
        Move m = new Move(dir);
        Location nextLoc = last.nextLocation(m);
        if (nextLoc.equals(to) || grid.possibleMove(last, m)) {
            /*
             * this move is allowed, it leads to the goal, or the way is clear
             */
            if (!path.hasLoop(nextLoc)) {
                int dd = path.size() + 1 + nextLoc.distance(to);
                Locations newPath = new Locations(path, nextLoc, dd);
                /* calculate the cost + heuristic value */
                queue.add(newPath);
            }
        }
    }

    /*
     * generate a path and return the first move
     */
    private Move getNextMoveFromPath(Grid grid, Location from, Location to) {
        return null;
    }

    /*
     * get a next move towards a goal. This returns a random move 20% of the time in
     * order to avoid local minima
     */
    public Move getNextMove(Grid grid, Location from, Location to) {
        Move m = null;
        float chance = random.nextFloat();
        if (chance > 0.80) {
            /*
             * with 20% chance pick a move at random instead of the normal move. This avoids
             * getting stuck in local minima
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

    private class Locations implements Comparable<Locations> {
        private Location from;
        private List<Location> locations = new ArrayList<>();
        private int cost;

        public Locations(Location from, int cost) {
            this.from = from;
            this.cost = cost;
            locations.add(from);
        }

        public Locations(Locations path, Location nextLoc, int dd) {
            this.from = path.from;
            this.cost = dd;
            this.locations.addAll(path.locations);
            this.locations.add(nextLoc);
        }

        public List<Location> getLocations() {
            return locations;
        }

        public boolean hasLoop(Location nextLoc) {
            Iterator<Location> it = locations.iterator();
            while (it.hasNext()) {
                Location loc = it.next();
                if (loc.equals(nextLoc)) {
                    return true;
                }
            }
            return false;
        }

        /*
         * translate a list of Locations to a Path (a list of locations)
         */
        public Path createPath() {
            Path path = new Path();
            Iterator<Location> it = locations.iterator();
            Location last = it.next();
            while (it.hasNext()) {
                Location loc = it.next();
                Move m = last.getMove(loc);
                path.addMove(m);
                last = loc;
            }
            return path;
        }

        public Location getLast() {
            return locations.get(locations.size() - 1);
        }

        public int size() {
            return locations.size();
        }

        public int compareTo(Locations other) {
            return cost - other.cost;
        }

    }
}
