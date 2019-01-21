package mas;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/*
 * List of Moves
 * @author jos
 */
public class Path {
    
    public List getMoves() {
        return moves;
    }

    public void setMoves(List moves) {
        this.moves = moves;
    }
    
    public void addMove(Move m) {
        moves.add(m);
    }

    private List moves = new ArrayList();
    
    public String toString() {
        Iterator it = moves.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Move m = (Move) it.next();
            sb.append(m.toString()).append("->");
        }
        return sb.toString();
    }
}
