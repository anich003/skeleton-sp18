package byog.StdDrawDemo;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        int width = 400;
        int height = 300;
        StdDraw.setCanvasSize(width, height);
        StdDraw.clear(StdDraw.GRAY);
        StdDraw.enableDoubleBuffering();
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 30));
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.text(width / 2, height / 2, "HELLO");
        StdDraw.show();
    }
}
