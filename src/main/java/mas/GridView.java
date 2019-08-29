package mas;

/*
 * Interface for all views on the grid.
 * @author jos
 */
public interface GridView {
    public void gridChanged();
    public GridController getController();
}
