package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Color;
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


         // Build map in center of room as a starting point
         map[width/2][height/2] = Tileset.FLOWER;
         int roomWidth = 10;
         int roomHeight = 8;
         int xmin = getRoomLowerBound(width/2, roomWidth);
         int ymin = getRoomLowerBound(height/2, roomHeight);
         Room r = new Room(xmin, ymin, roomWidth, roomHeight);
         r.addTo(map);

         int numberOfFeatures = 1;

         // Initialize variables to appease compiler gods but if not defined by
        // feature building step, then won't be used on this iteration of numberOfAttempts
        int newx = 0;
        int newy = 0;
        Direction d = null;

        // TODO Number of attempts should scale with size of map
        for (int numberOfAttempts = 0; numberOfAttempts < 1000; numberOfAttempts += 1) {

            // Find a random wall and normal direction in 1000 attempts
            for (int wallAttempts = 0; wallAttempts < 1000; wallAttempts += 1) {
                newx = RandomUtils.uniform(RNG, 1, width - 1);
                newy = RandomUtils.uniform(RNG, 1, height - 1);

                if (getTile(map, newx, newy) != Tileset.WALL) {
                    continue;
                }

                d = getNormalDirection(map, newx, newy);

                if (d != null && numberOfFeatures < 20) {
                    buildRandomFeature(map, newx, newy, d);
                    numberOfFeatures += 1;
                }
            }
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

    private TETile getTile(TETile[][] map, int xCoord, int yCoord) {
        return map[xCoord][yCoord];
    }

    /** Returns the outward Direction from a given wall, or null if unable to calculate */
    private Direction getNormalDirection(TETile[][] map, int xCoord, int yCoord) {
        try {
            if (map[xCoord][yCoord - 1] == Tileset.FLOOR) {
                return Direction.Up;
            } else if (map[xCoord][yCoord + 1] == Tileset.FLOOR) {
                return Direction.Down;
            } else if (map[xCoord - 1][yCoord] == Tileset.FLOOR) {
                return Direction.Right;
            } else if (map[xCoord + 1][yCoord] == Tileset.FLOOR) {
                return Direction.Left;
            } else {
                return null;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wall is too close to border. Skipping");
            return null;
        }
    }

    private void buildRandomFeature(TETile[][] map, int xCoord, int yCoord, Direction d) {
        final int ChanceRoom = 40;
        Room r = null;

        // Build either a random room or a random hallway
        int feature = RandomUtils.uniform(RNG, 0, 100);
        if (feature <= ChanceRoom) {
            int randomWidth = 4;
            int randomHeight = 4;

            for (int roomAttempts = 0; roomAttempts < 100; roomAttempts += 1) {
                randomWidth = RandomUtils.uniform(RNG, 4, 15);
                randomHeight = RandomUtils.uniform(RNG, 4, 10);
                r = new Room(xCoord, yCoord, randomWidth, randomHeight, d);
                if (r.isValid(map)) {
                    r.addTo(map);
                    connectFeatures(map, xCoord, yCoord, d);
                    break;
                }
            }
        } else {
            // Build a random hallway
            int randomLength = 3;
            // Hallways are essentially Rooms with pre-known width or height
            for (int hallwayAttempts = 0; hallwayAttempts < 100; hallwayAttempts += 1) {
                randomLength = RandomUtils.uniform(RNG, 5, 15);
                if (d == Direction.Up || d == Direction.Down) {
                    r = new Room(xCoord, yCoord, 3, randomLength, d);
                } else if (d == Direction.Left || d == Direction.Right) {
                    r = new Room(xCoord, yCoord, randomLength, 3, d);
                }

                if (r.isValid(map)) {
                    r.addTo(map);
                    connectFeatures(map, xCoord, yCoord, d);
                    break;
                }
            }
        }
    }

    private void connectFeatures(TETile[][] map, int xCoord, int yCoord, Direction d) {
        int xmod = 0;
        int ymod = 0;

        switch (d) {
            case Up:
                xmod =  0; ymod =  1; break;
            case Down:
                xmod =  0; ymod = -1; break;
            case Left:
                xmod = -1; ymod =  0; break;
            case Right:
                xmod =  1; ymod =  0; break;
            default:
                throw new RuntimeException("Error connecting features");
        }

        map[xCoord][yCoord] = Tileset.FLOOR;
        map[xCoord + xmod][yCoord + ymod] = Tileset.FLOOR;
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
            // check if xmax and ymax are within map bounds
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
        int height = 50;
        MapGenerator mg = new MapGenerator(1234);
        TETile[][] map = mg.generateRandomMap(width, height);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(map);
    }
}
