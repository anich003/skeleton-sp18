package byog.lab5;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import org.junit.Test;
import static org.junit.Assert.*;

public class Hexagon {
    private int size;
    private int xmin;
    private int ymin;
    private TETile tile;

    public Hexagon(int size, int xmin, int ymin, TETile tile) {
        this.size = size;
        this.xmin = xmin;
        this.ymin = ymin;
        this.tile = tile;
    }

    /** Calculates the maximum width of Hexagon */
    public int width() {
        return size == 1 ? 3 : 3 * size - 2;
    }

    public int xmin() { return xmin; }
    public int xmax() { return xmin + width() - 1; }
    public int ymin() { return ymin; }
    public int ymax() { return ymin + 2 * size - 1; }
    public int size() { return size; }
    public TETile tile() { return tile; }

    /** TESSELATION METHODS */
    /** Builds a new Hexagon made of TETile tiles that fits to the upper right of this Hexagon object */
    public Hexagon grow_right_up(TETile tile) {
        int new_xmin = xmax() - (size - 2);
        int new_ymin = ymin() + size;

        return new Hexagon(size, new_xmin, new_ymin, tile);
    }

    public Hexagon grow_right_down(TETile tile) {
        int new_xmin = xmax() - (size - 2);
        int new_ymin = ymin() - size;

        return new Hexagon(size, new_xmin, new_ymin, tile);
    }

    public Hexagon grow_left_down(TETile tile) {
        int new_xmin = xmin() - 2 * size + 1;
        int new_ymin = ymin() - size;

        return new Hexagon(size, new_xmin, new_ymin, tile);
    }

    public Hexagon grow_left_up(TETile tile) {
        int new_xmin = xmin() - 2 * size + 1;
        int new_ymin = ymin() + size;

        return new Hexagon(size, new_xmin, new_ymin, tile);
    }
}
