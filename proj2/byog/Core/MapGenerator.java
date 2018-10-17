package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Map;
import java.util.Random;

public class MapGenerator {
    private long seed;
    private Random RNG;

    public MapGenerator() {
        seed = System.currentTimeMillis();
        RNG = new Random(seed);
    }

    public MapGenerator(long seed) {
        this.seed = seed;
        RNG = new Random(seed);
    }

    public TETile[][] generateRandomMap(int width, int height) {
        TETile[][] map = initializeMap(width, height);
        return map;
    }

    private TETile[][] initializeMap(int width, int height) {
        TETile[][] map = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                map[x][y] = Tileset.NOTHING;
            }
        }
        return map;
    }
    public static void main(String[] args) {
        int width = 80;
        int height = 40;
        MapGenerator mg = new MapGenerator(1234);
        TETile[][] map = mg.generateRandomMap(width, height);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(map);
    }
}
