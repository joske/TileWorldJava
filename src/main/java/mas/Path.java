package mas;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/*
 * List of Moves
 * @author jos
 */
public class Path {

    public Path() {
    }

    public Path(List<Move> moves) {
        this.moves = moves;
    }
    
    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
    
    public void addMove(Move m) {
        moves.add(m);
    }

    private List<Move> moves = new ArrayList<>();
    
    public String toString() {
        Iterator<Move> it = moves.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Move m = it.next();
            sb.append(m.toString()).append("->");
        }
        return sb.toString();
    }
}
