package mas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class SearchStrategyTest {

    @Test
    public void testRightDown() {
        Grid g = new Grid(3, 3, 0, 0, 0, 0);
        SearchStrategy strategy = new SearchStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(1, 1);
        Path path = strategy.generatePath(g, from, to);
        assertNotNull(path);
        assertEquals(2, path.getMoves().size());
        assertEquals(Move.DOWN, path.getMoves().get(0).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(1).direction);
        assertEquals(to, lastLocation(from, path));

    }

    @Test
    public void testDown() {
        Grid g = new Grid(3, 3, 0, 0, 0, 0);
        SearchStrategy strategy = new SearchStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(2, 0);
        Path path = strategy.generatePath(g, from, to);
        assertNotNull(path);
        assertEquals(2, path.getMoves().size());
        assertEquals(Move.DOWN, path.getMoves().get(0).direction);
        assertEquals(Move.DOWN, path.getMoves().get(1).direction);
        assertEquals(to, lastLocation(from, path));
    }

    @Test
    public void testWithObstacle() {
        // s . .
        // . o o
        // . . e
        Grid g = new Grid(3, 3, 0, 0, 0, 0);
        Obstacle o = new Obstacle(1, 1);
        g.setObject(o, 1, 1);
        g.setObject(o, 1, 2);
        SearchStrategy strategy = new SearchStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(2, 2);
        Path path = strategy.generatePath(g, from, to);
        assertNotNull(path);
        assertEquals(4, path.getMoves().size());
        assertEquals(Move.DOWN, path.getMoves().get(0).direction);
        assertEquals(Move.DOWN, path.getMoves().get(1).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(2).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(3).direction);
        assertEquals(to, lastLocation(from, path));
    }

    @Test
    public void testWithObstacle2() {
        // s . .
        // o o .
        // . . e
        Grid g = new Grid(3, 3, 0, 0, 0, 0);
        Obstacle o = new Obstacle(1, 1);
        g.setObject(o, 1, 0);
        g.setObject(o, 1, 1);
        SearchStrategy strategy = new SearchStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(2, 2);
        Path path = strategy.generatePath(g, from, to);
        assertNotNull(path);
        assertEquals(4, path.getMoves().size());
        assertEquals(Move.RIGHT, path.getMoves().get(0).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(1).direction);
        assertEquals(Move.DOWN, path.getMoves().get(2).direction);
        assertEquals(Move.DOWN, path.getMoves().get(3).direction);
        assertEquals(to, lastLocation(from, path));
    }

    @Test
    public void testWithObstacle3() {
        // s . . o
        // . o o .
        // . . . .
        // o o . e
        Grid g = new Grid(4, 4, 0, 0, 0, 0);
        Obstacle o = new Obstacle(1, 1);
        g.setObject(o, 0, 3);
        g.setObject(o, 1, 1);
        g.setObject(o, 1, 2);
        g.setObject(o, 3, 0);
        g.setObject(o, 3, 1);
        SearchStrategy strategy = new SearchStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(3, 3);
        Path path = strategy.generatePath(g, from, to);
        assertNotNull(path);
        assertEquals(6, path.getMoves().size());
        assertEquals(Move.DOWN, path.getMoves().get(0).direction);
        assertEquals(Move.DOWN, path.getMoves().get(1).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(2).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(3).direction);
        assertEquals(Move.DOWN, path.getMoves().get(4).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(5).direction);
        assertEquals(to, lastLocation(from, path));
    }

    @Test
    public void testWithObstacle4() {
        // e . . o
        // . o o .
        // . . . .
        // o o . s
        Grid g = new Grid(4, 4, 0, 0, 0, 0);
        Obstacle o = new Obstacle(1, 1);
        g.setObject(o, 0, 3);
        g.setObject(o, 1, 1);
        g.setObject(o, 1, 2);
        g.setObject(o, 3, 0);
        g.setObject(o, 3, 1);
        SearchStrategy strategy = new SearchStrategy();

        Location to = new Location(0, 0);
        Location from = new Location(3, 3);
        Path path = strategy.generatePath(g, from, to);
        assertNotNull(path);
        assertEquals(6, path.getMoves().size());
        assertEquals(Move.LEFT, path.getMoves().get(0).direction);
        assertEquals(Move.UP, path.getMoves().get(1).direction);
        assertEquals(Move.LEFT, path.getMoves().get(2).direction);
        assertEquals(Move.LEFT, path.getMoves().get(3).direction);
        assertEquals(Move.UP, path.getMoves().get(4).direction);
        assertEquals(Move.UP, path.getMoves().get(5).direction);
        assertEquals(to, lastLocation(from, path));
    }

    @Test
    public void testBigGrid() {
        Grid g = new Grid(40, 40, 0, 0, 0, 0);
        // s . . o
        // . o o .
        // . . . .
        // o o . e
        Obstacle o = new Obstacle(1, 1);
        g.setObject(o, 0, 3);
        g.setObject(o, 1, 1);
        g.setObject(o, 1, 2);
        g.setObject(o, 3, 0);
        g.setObject(o, 3, 1);
        SearchStrategy strategy = new SearchStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(30, 30);
        Path path = strategy.generatePath(g, from, to);
        assertNotNull(path);
        assertEquals(60, path.getMoves().size());
        assertEquals(to, lastLocation(from, path));
    }

    public static Location lastLocation(Location from, Path path) {
        Location current = from;
        List<Move> moves = path.getMoves();
        for (Move m : moves) {
            current = current.nextLocation(m);
        }
        return current;
    }
}
