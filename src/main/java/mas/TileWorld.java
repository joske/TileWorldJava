package mas;

public class TileWorld {

    public TileWorld() {
        new Grid(40, 40, 75, 25, 25, 1).start();
    }
    
    public static void main(String[] args) {
        new TileWorld();
    }
}
