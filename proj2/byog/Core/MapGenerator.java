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

        map[width/2][height/2] = Tileset.FLOWER;
        int roomWidth = 10;
        int roomHeight = 8;
        int x = getRoomLowerBound(width/2, roomWidth);
        int y = getRoomLowerBound(height/2, roomHeight);

        Room r = new Room(x, y, roomWidth, roomHeight);
        r.addTo(map);

        r = new Room(10, 10, 7, 9, Direction.Right);
        r.addTo(map);
        map[10][10] = Tileset.FLOWER;

        map[13][14] = Tileset.FLOWER;
        r = new Room(13, 14, 5, 11, Direction.Up);
        if (r.isValid(map)) {
            // Should be valid
            r.addTo(map);
        }

        map[13][14] = Tileset.FLOOR;

        r = new Room(2, 2, 5, 5, Direction.Down);
        if (r.isValid(map)) {
            // should fail
            r.addTo(map);
        } else {
            System.out.println("Skipping: " + r);
        }

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

    private enum Direction { Up, Down, Left, Right };

    private int getRoomLowerBound(int center, int length) {
        return center - length / 2;
    }

    private int getRoomUpperBound(int center, int length) {
        return center + (length + 1) / 2;
    }

    private class Room {
        private int xmin;
        private int ymin;
        private int width;
        private int height;

        public Room(int xmin, int ymin, int width, int height) {
            this.xmin = xmin;
            this.ymin = ymin;
            this.width = width;
            this.height = height;
        }

        public Room(int xCenter, int yCenter, int width, int height, Direction d) {
            this.width = width;
            this.height = height;

            switch (d) {
                case Up:
                    this.xmin = getRoomLowerBound(xCenter, width);
                    this.ymin = yCenter;
                    break;
                case Down:
                    this.xmin = getRoomLowerBound(xCenter, width);
                    this.ymin = yCenter - height;
                    break;
                case Left:
                    this.xmin = xCenter - width;
                    this.ymin = getRoomLowerBound(yCenter, height);
                    break;
                case Right:
                    this.xmin = xCenter;
                    this.ymin = getRoomLowerBound(yCenter, height);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Direction");
            }
        }

        public void addTo(TETile[][] map) {
            for (int y = 0; y < height; y += 1) {
                for (int x = 0; x < width; x += 1) {
                    if (y == 0 || y == height - 1) {
                        map[x + xmin][y + ymin] = Tileset.WALL;
                    } else if (x == 0 || x == width - 1) {
                        map[x + xmin][y + ymin] = Tileset.WALL;
                    } else {
                        map[x + xmin][y + ymin] = Tileset.FLOOR;
                    }
                }
            }
        }

        public boolean isValid(TETile[][] map) {
            int map_width = map.length;
            int map_height = map[0].length;

            // check if xmin and ymin are in the map
            if (xmin < 0 || ymin < 0) { return false; }
            if (xmin + width > map_width || ymin + height > map_height) { return false; }

            // check if any room tiles land on existing floor tiles
            for (int x = 0; x < width; x += 1) {
                for (int y = 0; y < height; y += 1) {
                    if (map[x + xmin][y + ymin] == Tileset.FLOOR) {
                        return false;
                    }
                }
            }
            return true;
        }

        public String toString() {
            return "Room(" + xmin + ", " + ymin + ", " + width + ", " + height + ")";
        }
    }

    public static void main(String[] args) {
        int width = 80;
        int height = 30;
        MapGenerator mg = new MapGenerator(1234);
        TETile[][] map = mg.generateRandomMap(width, height);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(map);
    }
}
