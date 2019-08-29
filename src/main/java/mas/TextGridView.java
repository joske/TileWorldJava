package mas;

/*
 * Text based GridView
 * @author jos
 */
public class TextGridView implements GridView {
    
    public static final int MAX_TIME = 100;
    private Grid grid; 
    private GridController controller;
    
    public TextGridView(Grid grid) {
        this.grid = grid;
        drawGrid();
        controller = new TextGridController();
    }
    
    public GridController getController() {
        return controller;
    }
    
    public void gridChanged() {
        drawGrid();
    }
    
    private void drawGrid() {
        for (int r = 0; r <= grid.getRows(); r++) {
            for (int c = 0; c <= grid.getCols(); c++) {
                char i;
                if (r == 0 || r == grid.getRows() ||
                    c == 0 || c == grid.getCols()) {
                    i = '#';
                } else { 
                    GridObject o = grid.getObjectAt(r, c);
                    if (o instanceof Hole) {
	                    i = Character.forDigit(((Hole)o).getScore(), 10);
	                } else if (o instanceof Obstacle) {
	                    i = '#';
	                } else if (o instanceof Tile) {
	                    i = 'T';
	                } else if (o instanceof Agent) {
	                    i = 'a';
	                } else {
	                    i = ' ';
	                }
                }
                System.out.print(i);
            }
            System.out.println();
        }
    }
    
    class TextGridController implements GridController {
        
        public void startGrid() {
            Thread t = new Thread() {
        	    public void run() {
        	        for (int t = 0; t < MAX_TIME; t++) {
        	            grid.update();
        	        }
        	    }                
            };
            t.start();
        }
    }
    
}
