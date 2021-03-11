package mas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AStarStrategyTest {

    @Test
    public void testRightDown() {
        Grid g = new Grid(3, 3, 0, 0, 0, 0);
        AStarStrategy strategy = new AStarStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(1, 1);
        Path path = strategy.generatePath(g, from, to);
        assertEquals(2, path.getMoves().size());
        assertEquals(Move.RIGHT, path.getMoves().get(0).direction);
        assertEquals(Move.DOWN, path.getMoves().get(1).direction);
    }

    @Test
    public void testDown() {
        Grid g = new Grid(3, 3, 0, 0, 0, 0);
        AStarStrategy strategy = new AStarStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(2, 0);
        Path path = strategy.generatePath(g, from, to);
        assertEquals(2, path.getMoves().size());
        assertEquals(Move.DOWN, path.getMoves().get(0).direction);
        assertEquals(Move.DOWN, path.getMoves().get(1).direction);
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
        AStarStrategy strategy = new AStarStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(2, 2);
        Path path = strategy.generatePath(g, from, to);
        assertEquals(4, path.getMoves().size());
        assertEquals(Move.DOWN, path.getMoves().get(0).direction);
        assertEquals(Move.DOWN, path.getMoves().get(1).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(2).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(3).direction);
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
        AStarStrategy strategy = new AStarStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(2, 2);
        Path path = strategy.generatePath(g, from, to);
        assertEquals(4, path.getMoves().size());
        assertEquals(Move.RIGHT, path.getMoves().get(0).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(1).direction);
        assertEquals(Move.DOWN, path.getMoves().get(2).direction);
        assertEquals(Move.DOWN, path.getMoves().get(3).direction);
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
        AStarStrategy strategy = new AStarStrategy();

        Location from = new Location(0, 0);
        Location to = new Location(3, 3);
        Path path = strategy.generatePath(g, from, to);
        assertEquals(6, path.getMoves().size());
        assertEquals(Move.DOWN, path.getMoves().get(0).direction);
        assertEquals(Move.DOWN, path.getMoves().get(1).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(2).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(3).direction);
        assertEquals(Move.RIGHT, path.getMoves().get(4).direction);
        assertEquals(Move.DOWN, path.getMoves().get(5).direction);
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
        AStarStrategy strategy = new AStarStrategy();

        Location to = new Location(0, 0);
        Location from = new Location(3, 3);
        Path path = strategy.generatePath(g, from, to);
        assertEquals(6, path.getMoves().size());
        assertEquals(Move.UP, path.getMoves().get(0).direction);
        assertEquals(Move.LEFT, path.getMoves().get(1).direction);
        assertEquals(Move.LEFT, path.getMoves().get(2).direction);
        assertEquals(Move.LEFT, path.getMoves().get(3).direction);
        assertEquals(Move.UP, path.getMoves().get(4).direction);
        assertEquals(Move.UP, path.getMoves().get(5).direction);
    }

}
