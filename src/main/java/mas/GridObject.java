package mas;

/*
 * Base class for all objects in the grid
 */
public abstract class GridObject {
    protected Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String toString() {
        return location.toString();
    }

}
