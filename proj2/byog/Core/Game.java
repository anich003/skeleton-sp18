package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import java.util.Random;

import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import javax.sound.midi.SysexMessage;
import java.awt.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private Random rng;

    Font titleFont = new Font("Monaco", Font.BOLD, 45);
    Font optionsFont = new Font("Monaco", Font.PLAIN, 26);

    int mainMenuWidth= 800;
    int mainMenuHeight = 600;

    public Game() {
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        // Draw main menu
        drawMainMenu();

        while(!StdDraw.hasNextKeyTyped()) { continue; }

        char key = StdDraw.nextKeyTyped();
        switch (key) {
            case 'n':
                long seed = getSeed();
                playGame(seed);
                break;
            case 'l':
                System.out.println("Loading game.ser");
                // Load game
                break;
            case 'q':
                System.exit(0);
                break;
            default:
                System.out.println("You didn't hit the right shit");
                playWithKeyboard();
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    private void drawMainMenu() {

        StdDraw.setCanvasSize(mainMenuWidth, mainMenuHeight);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);

        StdDraw.setFont(titleFont);
        StdDraw.text(0.5, 0.75, "CS61B: The Game");

        StdDraw.setFont(optionsFont);
        StdDraw.text(0.5, 0.4, "(n)ew Game");
        StdDraw.text(0.5, 0.35, "(l)oad Game");
        StdDraw.text(0.5, 0.3, "(q)uit");

        StdDraw.show();
    }

    private void drawNewGameScreen(String seedString) {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setFont(optionsFont);
        StdDraw.text(0.5, 0.6, "Enter a new seed. Press 's' to finish");
        StdDraw.text(0.5, 0.5, seedString);
        StdDraw.show(); // enableDoubleBuffering requires you to explicitly call show
    }

    private long getSeed() {
        long seed = -1;
        String input = "";
        drawNewGameScreen(input);
        // get user input for string and make sure is valid
        while (true) {
            while (!StdDraw.hasNextKeyTyped()) { continue; }
            char key = StdDraw.nextKeyTyped();
            if (key == 's') { break; }
            input += String.valueOf(key);
            drawNewGameScreen(input);
        }
        try {
            seed = Long.parseLong(input);
            if (seed == -1)
                throw new NumberFormatException("Could not parse seed. Try again!");
        } catch (NumberFormatException nfe){
            drawNewGameScreen("Invalid seed. Only use digits!");
            StdDraw.pause(1000);
            getSeed();
        }
        return seed;
    }

    private void playGame(long seed) {
        rng = new Random(seed);
        MapGenerator mg = new MapGenerator(seed);
        TETile[][] map = mg.generateRandomMap(WIDTH, HEIGHT);
        GameState gs = new GameState(map);
        ter.initialize(WIDTH, HEIGHT);

        // main game loop; handles interactive events and updates map
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped(); // pop last key
                switch (key) {
                    case 'w': gs.moveUp();    break;
                    case 's': gs.moveDown();  break;
                    case 'a': gs.moveLeft();  break;
                    case 'd': gs.moveRight(); break;
                    case ' ': gs.teleport();  break;
                    default: System.out.println("Invalid key. Can only use wasd. You pressed: " + key); break;
                }
            }
            if (gs.isGameOver()) {
                endGame("You win!");
            }

            ter.renderFrame(gs.getMap());
            // do this in GameState object
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(1, HEIGHT - 1, gs.getTileDescription(StdDraw.mouseX(), StdDraw.mouseY()));
            StdDraw.show();
            StdDraw.pause(1000 / 60);
        }
    }

    private int getTileCoordinate(double coord) {
        return (int) coord;
    }

    private void endGame(String message) {
        StdDraw.setCanvasSize(800,600);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setFont(titleFont);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5, 0.5, message);
        StdDraw.show();
        while (!StdDraw.hasNextKeyTyped()) { continue; }
        System.exit(0);
    }

    private class GameState {
        TETile[][] map;
        private int playerX;
        private int playerY;
        private int keyX;
        private int keyY;
        private boolean hasKey;
        private boolean gameOver;

        public GameState(TETile[][] map) {
            this.map = map;
            // player always starts in middle of the map
            playerX = map.length / 2;
            playerY = map[0].length / 2;
            hasKey = false;
            gameOver = false;

            // TODO Factor out get random location into MapUtilities
            while (true) {
                // Game.seed should be set before we get here...
                keyX = RandomUtils.uniform(rng, 0, WIDTH - 1);
                keyY = RandomUtils.uniform(rng, 0, HEIGHT - 1);
                if (map[keyX][keyY] == Tileset.FLOOR) {
                    // if keyX and keyY hit a Floor then move on with our lives.
                    break;
                }
            }
        }

        public TETile[][] getMap() {
            TETile[][] mapCopy = TETile.copyOf(map);

            mapCopy[playerX][playerY] = Tileset.PLAYER;

            if (!hasKey) {
                mapCopy[keyX][keyY] = Tileset.FLOWER;
            }

            return mapCopy;
        }

        private void movePlayer(int dx, int dy) {
            if (map[playerX + dx][playerY + dy] == Tileset.LOCKED_DOOR && hasKey) {
                gameOver = true;
                return;
            } else if (map[playerX + dx][playerY + dy] == Tileset.FLOOR) {
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
}
