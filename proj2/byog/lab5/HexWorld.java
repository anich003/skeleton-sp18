package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
import java.util.*;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    private static final Random RANDOM = new Random();

    private static int calculateBboxSize(int hex_sides) {
        return hex_sides == 1 ? 3 : 3 * hex_sides - 2;
    }

    private static TETile randomTile() {
        int tile_num = RANDOM.nextInt(3);
        switch (tile_num) {
            case 0: return Tileset.WATER;
            case 1: return Tileset.SAND;
            case 2: return Tileset.FLOWER;
            default: return Tileset.MOUNTAIN;
        }
    }

    /** Adds hexagon to world with top edge at top and left edge at left.
     * @param world 2D array of TETiles to which hexagon will be added
     * @param tile a TETile object to be used as material for hexagon
     * @param xcoord the left tile coordinate of the hexagon
     * @param ycoord the bottom tile coordinate of the hexagon
     **/
    public static void addHexagon(TETile[][] world, TETile tile, int xcoord, int ycoord, int hex_size) {
        int bbox_dim = calculateBboxSize(hex_size);

        int world_Xmax = world.length;
        int world_Ymax = world[0].length;

        if (xcoord + bbox_dim >= world_Xmax - 1 | ycoord + bbox_dim >= world_Ymax - 1)
            throw new RuntimeException("ERROR: Hexagon does not fit in world");

        // Build hexagon from middle-up and middle-down
        // NOTE: world[0][0] is at bottom left
        int x_start = xcoord;
        int y_start_up = ycoord + hex_size;
        int y_start_dn = ycoord + hex_size - 1;
        int blank_cols = 0;

        // Draw hexagon using internal coordinates hex_x and hex_y starting middle out
        for (int hex_y = 0; hex_y < hex_size; hex_y += 1) {
            for (int hex_x = 0 + blank_cols; hex_x < bbox_dim - blank_cols; hex_x += 1) {

                // middle-up
                world[x_start + hex_x][y_start_up + hex_y] = tile;

                // middle-dn
                world[x_start + hex_x][y_start_dn - hex_y] = tile;
            }
            blank_cols += 1;
        }
    }

    public static void addHexagon(TETile[][] world, Hexagon hexagon) {
        addHexagon(world, hexagon.tile(), hexagon.xmin(), hexagon.ymin(), hexagon.size());
    }


    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Queue<Hexagon> hexQueue = new LinkedList<>();

        Hexagon seedHex = new Hexagon(3, 15, 0, Tileset.MOUNTAIN);
        hexQueue.add(seedHex);

        while (!hexQueue.isEmpty()) {
            seedHex = hexQueue.remove();
            addHexagon(world, seedHex);
            Hexagon L = seedHex.grow_left_up(randomTile());
            Hexagon R = seedHex.grow_right_up(randomTile());

            try {
                addHexagon(world, L);
                hexQueue.add(L);
            } catch (Exception e) {
                System.out.println("Skipping Hexagon");
            }

            try {
                addHexagon(world, R);
                hexQueue.add(R);
            } catch (Exception e) {
                System.out.println("Skipping Hexagon");
            }

        }

        // draws the world to the screen
        ter.renderFrame(world);
    }


    /** Test Functions */
    private void testBboxKnownVsExpected (int hex_side, int expected) {
        int output = calculateBboxSize(hex_side);
        assertEquals("Calculated bounding box does not match expected output. Got: " + output + " expected: " + expected, (long) expected, output);
    }

    @Test
    public void testBoundingBox() {
        testBboxKnownVsExpected(1, 3);
        testBboxKnownVsExpected(3, 7);
        testBboxKnownVsExpected(6,16);
    }


}
