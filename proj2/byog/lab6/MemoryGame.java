package byog.lab6;
import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;

    private Font font;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    private StatusBar statusBar;

    private class StatusBar {
        private MemoryGame mg;
        private int height;
        private int lineYpos;
        private int textYpos;
        private Font font;
        private long lastEncouragement;
        private String currentEncouragement;

        public StatusBar(MemoryGame mg) {
            this.mg = mg;
            height = 2;
            lineYpos = mg.height - this.height;
            textYpos = mg.height - height / 2;
            font = new Font("Monaco", Font.PLAIN, 16);
            lastEncouragement = System.currentTimeMillis();
            currentEncouragement = ENCOURAGEMENT[0];
        }

        public void drawStatusBar() {
            // Set status bar pen configuration
            StdDraw.setFont(font);

            StdDraw.line(0, lineYpos, mg.width, lineYpos);

            StdDraw.text(mg.width / 2, textYpos , mg.playerTurn ? "Type!" : "Watch!");
            setEncouragement();
            StdDraw.textRight(mg.width - 2, textYpos, currentEncouragement);
            StdDraw.textLeft(2, textYpos, "Round " + mg.round);
        }

        private String getRandomEncouragement() {
            int index = rand.nextInt(ENCOURAGEMENT.length);
            return ENCOURAGEMENT[index];
        }

        /** */
        private void setEncouragement() {
            long now = System.currentTimeMillis();
            if ((now - lastEncouragement) > 5000) {
                currentEncouragement = getRandomEncouragement();
                lastEncouragement = System.currentTimeMillis();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);

        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        this.rand = new Random(seed);

        this.statusBar = new StatusBar(this);
    }

    public String generateRandomString(int n) {
        StringBuilder sb = new StringBuilder();
        int randIndex;
        for (int i = 0; i < n; i += 1) {
            randIndex = RandomUtils.uniform(rand, CHARACTERS.length);
            sb.append(CHARACTERS[randIndex]);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);

        if (!this.gameOver) {
            statusBar.drawStatusBar();
        }

        StdDraw.setFont(this.font);
        StdDraw.text(this.width / 2, this.height / 2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        clearScreen();
        for (int i = 0; i < letters.length(); i += 1) {
            char c = letters.charAt(i);
            drawFrame(Character.toString(c));
            sleep(1000);
            drawFrame("");
            sleep(500);
        }

        // dump any premature key presses before moving on
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }

//        playerTurn = false;
    }

    public String solicitNCharsInput(int n) {
        drawFrame("");
        System.out.println("Soliciting characters from user...");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i += 1) {
            // wait until user inputs next key
            while (!StdDraw.hasNextKeyTyped()) { }

            sb.append(StdDraw.nextKeyTyped());
            drawFrame(sb.toString());
        }
        System.out.println("Done soliciting characters from user");
        System.out.println("Received: " + sb.toString());
        return sb.toString();
    }

    private void clearScreen() {
        StdDraw.clear(Color.black);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        this.round = 1;
        this.playerTurn = false;
        this.gameOver = false;

        String randomString;
        String playerString;
        //TODO: Establish Game loop
        while (!gameOver) {
            clearScreen();
            drawFrame("Round " + this.round);
            sleep(1000);
            randomString = generateRandomString(round);
            System.out.println("Random Word is: " + randomString);

            if (!playerTurn) {
                this.flashSequence(randomString);
            }

            playerTurn = true;
            playerString = solicitNCharsInput(round);

            if (playerString.equals(randomString)) {
                round += 1;
                drawFrame("Congratulations! Moving on...");
                sleep(1000);
            } else {
                drawFrame("Game Over! You made it to round " + this.round);
                gameOver = true;
            }

            playerTurn = false;
        }
    }

}
