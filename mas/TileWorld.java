package mas;

public class TileWorld {

    public TileWorld() {
        grid = new Grid(40, 40, 75, 25, 25, 6);
    }
    
    private Grid grid;

    public static void main(String[] args) {
        TileWorld tw = new TileWorld();
    }
}
