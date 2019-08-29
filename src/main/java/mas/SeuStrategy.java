package mas;

import java.util.Iterator;

/*
 * This class looks for the best Hole based on the
 * expected utility. This takes into account the distance
 * of the agent to the Hole, the score of the Hole, and
 * the distance of the nearest Tile to the Hole
 * @author jos
 */
public class SeuStrategy implements HoleStrategy {
    
    public Hole getBestHole(Grid grid, Location agent) {
        float maxSeu = 0;
        Hole maxHole = null;
        
        Iterator it = grid.getHoles().iterator();
        while(it.hasNext()) {
            Hole h = (Hole)it.next();
            float seu = calculateSEU(grid, h, agent);
            if (seu > maxSeu) {
                maxSeu = seu;
                maxHole = h;
            }
        }
        return maxHole;
    }
    
    private float calculateSEU(Grid grid, Hole h, Location agent) {
        float seu = 0;
        Tile t = grid.getClosestTile(h.location);
        if (t != null) {
	        int dist = h.location.distance(t.location);
	        seu = 1.0f * h.getScore() / (agent.distance(h.location) + dist);
        }
        return seu;
    }
}
