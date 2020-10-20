package mas;

import java.util.*;

/*
 * Class that implements a* algorithm
 * @author jos
 */
public class AStarStrategy implements PathStrategy {
        
    /**
     * This generates a path to the desired location
     * it uses a* algorithm
     * with manhattan-distance as extra heuristic
     */
    public Path generatePath(Grid grid, Location from, Location to) {
        Set<Node> openList = new HashSet<>();
        Set<Node> closedList = new HashSet<>();
        Map<Location, Integer> gscore = new HashMap<>(); 
        SortedSet<Node> fscore = new TreeSet<>(); 
        Node fromNode = new Node(from, Integer.MAX_VALUE);
        openList.add(fromNode);
        fscore.add(fromNode);
        while (!openList.isEmpty()) {
            Node current = fscore.first();
            if (current.location.equals(to)) {
                // goal reached
            }
            openList.remove(current);
            closedList.add(current);
            checkNeighbor(openList, closedList, current, Move.UP, from, to);
            checkNeighbor(openList, closedList, current, Move.RIGHT, from, to);
            checkNeighbor(openList, closedList, current, Move.DOWN, from, to);
            checkNeighbor(openList, closedList, current, Move.LEFT, from, to);
        }
        return null;
    }
        
    private void checkNeighbor(Set<Node> openList, Set<Node> closedList, Node current, int direction, Location from, Location to) {
        Move m = new Move(direction);
        Location nextLoc = current.location.nextLocation(m);
        int h = nextLoc.distance(to);
        int g = current.location.distance(from) + 1;
        Node neighbor = new Node(current.location.nextLocation(m), g + h);
        if (openList.contains(neighbor)) {
            openList.remove(neighbor);
        }
    }

    @Override
    public Move getNextMove(Grid grid, Location from, Location to) {
        return null;
    }

    private class Node implements Comparable<Node> {
        private Location location;
        private int fscore;
        
        public Node(Location location, int fscore) {
            this.location = location;
            this.fscore = fscore;
        }

        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node otherNode = (Node) other;
                return location == otherNode.location;

            }
            return false;
        }

        public int compareTo(Node other) {
            return fscore - other.fscore;
        }
    }
}
