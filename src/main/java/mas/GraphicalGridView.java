package mas;

import java.awt.*;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * GridView implementation that uses java 2D API to
 * draw the grid and all it contains. Also
 * displays the scores of all agents in appropriate
 * colors.
 */
public class GraphicalGridView extends JFrame implements GridView {

    private static final long serialVersionUID = 1L;

    protected static final int SLEEP_TIME = 200;
    
    private GraphicalGridController controller;
    private ScorePanel scorePanel;
    
    public GraphicalGridView(Grid grid) {
        setTitle("TileWorld");
        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());
        cont.add(new GridPanel(grid), "Center");
        controller = new GraphicalGridController(grid);
        cont.add(controller, "South");
        scorePanel = new ScorePanel(grid);
        cont.add(scorePanel, "East");
        pack();
        show();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    public GridController getController() {
        return controller;
    }
    
    public void gridChanged() {
        validate();
        repaint();
    }
    
    class ScorePanel extends JPanel {
        
        private static final long serialVersionUID = 1L;
        private Grid grid;
        private Dimension size = new Dimension(120, 100); 
        
        public ScorePanel(Grid grid) {
            this.grid = grid;
            setPreferredSize(size);
        }
        
        public void paintComponent(Graphics g) {
            int x = 10;
            int y = 30;
            Font normal = g.getFont();
            Font bold = new Font(normal.getName(), Font.BOLD, 16);
            FontMetrics fm = g.getFontMetrics(bold);
            g.setFont(bold);
            String scores = "Scores";
            int width = fm.stringWidth(scores);
            int center = (int)size.getWidth() / 2 - width / 2;
            g.drawString(scores, center, y);
            g.setFont(normal);
            y += 40;
            Iterator<Agent> it = grid.getAgents().iterator();
            while (it.hasNext()) {
                Agent a = it.next();
                String s = "Agent " + a.getId() + ": " + a.getScore();
                g.setColor(getAgentColor(a.getId()));
                g.drawString(s, x, y);
                y += 20;
            }
            
        }
        
    }
    
    class GridPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        private static final int magnifier = 15;
        private Grid grid;     
        
        public GridPanel(Grid grid) {
            this.grid = grid;
            Dimension size = new Dimension(grid.getCols() * magnifier + 1, 
                    					   grid.getRows() * magnifier + 1);
            setSize(size);
            setMinimumSize(size);
            setPreferredSize(size);
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.clearRect(0, 0, grid.getCols() * magnifier, grid.getRows() * magnifier);
            g.drawRect(0, 0, grid.getCols() * magnifier, grid.getRows() * magnifier);
            FontMetrics fm = g.getFontMetrics();
            int height = fm.getHeight();
            for (int r = 1; r < grid.getRows(); r++) {
                for (int c = 1; c < grid.getCols(); c++) {
                    GridObject o = grid.getObjectAt(r, c);
                    int x = c * magnifier;
                    int y = r * magnifier;
                    if (o instanceof Hole) {
                        String s = "" + ((Hole)o).getScore();
                        int sw = fm.stringWidth(s);
                        g.drawOval(x - magnifier / 2, y - magnifier / 2, magnifier, magnifier);
                        g.drawString(s, x - sw / 2, y + height / 2);
	                } else if (o instanceof Obstacle) {
	                    g.fillRect(x - magnifier / 2, y - magnifier / 2, magnifier, magnifier);
	                } else if (o instanceof Tile) {
	                    g.fillOval(x - magnifier / 2, y - magnifier / 2, magnifier, magnifier);
	                } else if (o instanceof Agent) {
	                    Agent a = ((Agent)o); 
	                    g.setColor(getAgentColor(a.getId()));
                        g.drawRect(x - magnifier / 2, y - magnifier / 2, magnifier, magnifier);
	                    if (a.hasTile()) {
	                        g.drawOval(x - magnifier / 2 + 1, y - magnifier / 2, magnifier, magnifier);
	                    }
	                    g.setColor(Color.black);
	                }
                    
                }
            }
        }
    }
    
    private Color getAgentColor(int id) {
        Color c = Color.black;
        switch (id % 10) {
        case 0 :
            c = Color.lightGray;
            break;
        case 1 :
            c = Color.green;
            break;
        case 2 : 
            c = Color.magenta;
            break;
        case 3 : 
            c = Color.red;
            break;
        case 4 : 
            c = Color.orange;
            break;
        case 5 : 
            c = Color.cyan;
            break;
        case 6 : 
            c = Color.blue;
            break;
        case 7 : 
            c = Color.pink;
            break;
        case 8 : 
            c = Color.darkGray;
            break;
        case 9 : 
            c = Color.yellow;
            break;
        }
        return c;
    }
    
    class GraphicalGridController extends JPanel implements GridController {
        private static final long serialVersionUID = 1L;
        JButton next = new JButton("next");
        JButton play = new JButton("play");
        JButton stop = new JButton("stop");
        Grid grid = null;
        boolean running = false;
        
        public GraphicalGridController(Grid grid) {
            this.grid = grid;
            next.setFocusable(true);
            play.setFocusable(true);
            stop.setFocusable(true);
            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getGrid().update();
                }
            });
            add(next);
            play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    running = true;
                    play.setEnabled(false);
                    next.setEnabled(false);
                    stop.setEnabled(true);
                }
            });
            add(play);
            stop.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    running = false;
                    play.setEnabled(true);
                    next.setEnabled(true);
                    stop.setEnabled(false);                    
                }
            });
            stop.setEnabled(false);
            add(stop);
        }
        
        protected Grid getGrid() {
            return grid;
        }
        
        public void startGrid() {
            Thread t = new Thread() {
                public void run() {
                    while (true) {
                        if (running) {
                            getGrid().update();
                        }
                        try {Thread.sleep(SLEEP_TIME);} catch(InterruptedException ie){}
                    }
                }
            };
            t.start();
        }
    }
}
