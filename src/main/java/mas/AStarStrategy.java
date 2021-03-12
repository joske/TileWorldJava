package mas;

import java.util.*;
import java.util.stream.Collectors;

/*
 * Class that implements a* algorithm
 * @author jos
 */
public class AStarStrategy implements PathStrategy {

    /**
     * This generates a path to the desired location it uses a* algorithm with
     * manhattan-distance as extra heuristic
     */
    public Path generatePath(Grid grid, Location from, Location to) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();
        Node fromNode = new Node(null, from, Integer.MAX_VALUE, 0);
        openList.add(fromNode);
        while (!openList.isEmpty()) {
            Node current = openList.poll();
            if (current.location.equals(to)) {
                // goal reached
                return makePath(current, fromNode);
            }
            closedList.add(current);
            checkNeighbor(grid, openList, closedList, current, Move.UP, from, to);
            checkNeighbor(grid, openList, closedList, current, Move.RIGHT, from, to);
            checkNeighbor(grid, openList, closedList, current, Move.DOWN, from, to);
            checkNeighbor(grid, openList, closedList, current, Move.LEFT, from, to);
        }
        return null;
    }

    private void checkNeighbor(Grid grid, PriorityQueue<Node> openList, Set<Node> closedList, Node current,
            int direction, Location from, Location to) {
        Move m = new Move(direction);
        Location nextLoc = current.location.nextLocation(m);
        if (nextLoc.equals(to) || grid.possibleMove(current.location, m)) {
            int h = nextLoc.distance(to);
            int g = current.location.distance(from) + 1;
            Node child = new Node(current, nextLoc, g, h);
            if (!closedList.contains(child)) {
                List<Node> sameLocation = openList.stream()
                        .filter((n) -> n.location.equals(child.location) && n.g > child.g).collect(Collectors.toList());
                if (sameLocation.isEmpty()) { // no node found with lower g
                    openList.add(child);
                }
            }
        }
    }

    private Path makePath(Node end, Node from) {
        List<Move> reverseMoves = new ArrayList<>();
        Node current = end;
        Node parent = end.parent;
        while (!current.equals(from)) {
            Move m = moveFromParent(current, parent);
            reverseMoves.add(m);
            current = parent;
            parent = current.parent;
        }
        Collections.reverse(reverseMoves);
        return new Path(reverseMoves);
    }

    private Move moveFromParent(Node current, Node parent) {
        if (parent.location.col == current.location.col) {
            if (parent.location.row == current.location.row - 1) {
                return new Move(Move.DOWN);
            } else {
                return new Move(Move.UP);
            }
        } else {
            if (parent.location.col == current.location.col - 1) {
                return new Move(Move.RIGHT);
            } else {
                return new Move(Move.LEFT);
            }
        }
    }

    @Override
    public Move getNextMove(Grid grid, Location from, Location to) {
        return null;
    }

    private class Node implements Comparable<Node> {
        private Location location;
        private int f;
        private int g;
        private Node parent;

        public Node(Node parent, Location location, int g, int h) {
            this.parent = parent;
            this.location = location;
            this.g = g;
            this.f = g + h;
        }

        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node otherNode = (Node) other;
                return location == otherNode.location;

            }
            return false;
        }

        public int compareTo(Node other) {
            return f - other.f;
        }

        public String toString() {
            return "node@" + location + " with g=" + g + " and f=" + f;
        }
    }
}
