package byog.Core;

import java.util.Random;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;

/* Object that holds the game state, including specific instance of map,
 * player and item coordinates and game-state flags like gameOver. */
public class GameState implements Serializable {
    private static final long serialVersionUID = 123456789L;

    TETile[][] map;
    Random rng;
    private int WIDTH;
    private int HEIGHT;
    private int playerX;
    private int playerY;
    private int keyX;
    private int keyY;
    private boolean hasKey;
    private boolean gameOver;

    public GameState(TETile[][] map, Random rng) {
        this.map = map;
        this.rng = rng;
        WIDTH = map.length;
        HEIGHT = map[0].length;

        // player starts in random location
        while (true) {
            playerX = RandomUtils.uniform(rng, 0, WIDTH - 1);
            playerY = RandomUtils.uniform(rng, 0, HEIGHT - 1);
            if (map[playerX][playerY].equals(Tileset.FLOOR)) { break; }
        }

        hasKey = false;
        gameOver = false;

        // TODO Factor out get random location into MapUtilities
        while (true) {
            // Game.seed should be set before we get here...
            keyX = RandomUtils.uniform(rng, 0, WIDTH - 1);
            keyY = RandomUtils.uniform(rng, 0, HEIGHT - 1);
            if (map[keyX][keyY].equals(Tileset.FLOOR)) {
                // if keyX and keyY hit a Floor then move on with our lives.
                break;
            }
        }
    }

    public TETile[][] getMap() {
        TETile[][] mapCopy = TETile.copyOf(map);

        mapCopy[playerX][playerY] = Tileset.PLAYER ;

        if (!hasKey) {
            mapCopy[keyX][keyY] = Tileset.FLOWER;
        }

        return mapCopy;
    }

    private void movePlayer(int dx, int dy) {
        if (map[playerX + dx][playerY + dy].equals(Tileset.LOCKED_DOOR)  && hasKey) {
            gameOver = true;
            return;
        }

        TETile proposedTile = map[playerX + dx][playerY + dy];
        if (proposedTile.equals(Tileset.FLOOR)) {
            playerX += dx;
            playerY += dy;
        }

        // pick up items
        if (playerX == keyX && playerY == keyY) {
            hasKey = true;
        }
    }

    public void moveUp()    { movePlayer( 0,  1); }
    public void moveDown()  { movePlayer( 0, -1); }
    public void moveLeft()  { movePlayer(-1,  0); }
    public void moveRight() { movePlayer( 1,  0); }

    public void teleport()  {
        while (true) {
            int randomX = RandomUtils.uniform(rng, 0, WIDTH - 1);
            int randomY = RandomUtils.uniform(rng, 0, HEIGHT - 1);
            if (map[randomX][randomY] == Tileset.FLOOR) {
                playerX = randomX;
                playerY = randomY;
                break;
            }
        }
    }

    public String getTileDescription(double x, double y) {
        int tileX = (int) x;
        int tileY = (int) y;
        // doesn't capture elements that are "floating" on map
        // things like Player and items aren't on the "map"
        return map[tileX][tileY].description();
    }

    public boolean isGameOver() { return gameOver; }
}