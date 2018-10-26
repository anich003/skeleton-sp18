package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import java.util.Random;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private Random rng;
    GameState gs;

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
                MapGenerator mg = new MapGenerator(seed);
                TETile[][] map = mg.generateRandomMap(WIDTH, HEIGHT);
                rng = new Random(seed);
                gs = new GameState(map, rng);
                playGameInteractively(gs);
                break;
            case 'l':
                System.out.println("Loading game.ser");
                gs = loadGameState();
                playGameInteractively(gs);
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

        // parse string
        // for each char
        //      execute action on world
        // return gs.getMap();

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

    private void playGameInteractively(GameState gs) {
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
                    case ':':
                        // wait until next key stroke to figure out what to do
                        while (!StdDraw.hasNextKeyTyped()) { continue; }
                        if (StdDraw.nextKeyTyped() == 'q') {
                            saveGameState(gs);
                            endGame("Saving. Press Cmd+Q to quit.");
                        }
                        break;
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

    private static void saveGameState(GameState gs) {
        File f = new File("./game-state.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(gs);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private static GameState loadGameState() {
        File f = new File("./game-state.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                GameState gs = (GameState) os.readObject();
                os.close();
                return gs;
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        // In case no world has been saved yet, we return a null object
        // should not hit this.
        return null;
    }
}
