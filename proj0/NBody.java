public class NBody {
    
    
    /** 
     * Reads the radius from the universe text file at fp. It is assumed
     * the second line of the file contains the universe radius.
     *
     *
     * @param fp the file path to the universe description
     * @return the universe radius
     */
    public static double readRadius(String fp) {
        In in = new In(fp);
        in.readDouble();                    // Throw away number of planets
        double radius = in.readDouble();    // Read in radius
        return radius;
    }

    /** 
     * Constructs an array of Planets from the universe description at fp.
     */
    public static Planet[] readPlanets(String fp) {
        In in = new In(fp);
        int numPlanets = in.readInt();                    
        in.readDouble();                    // Throw away radius

        Planet[] planets = new Planet[numPlanets];
        int idx = 0;
        /* Read until file is empty */
        while (idx < numPlanets) {
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String file = in.readString();

            planets[idx++] = new Planet(xPos, yPos, xVel, yVel, mass, file);

            System.out.println("Completed planet " + (idx - 1));
        }

        return planets;
    }

    public static void main (String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        double time = 0;
       
        StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();
        
        StdAudio.play("audio/2001.mid");

        while (time < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            StdDraw.clear();
            StdDraw.picture(0,0, "images/starfield.jpg");

            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
                planets[i].draw();
            }

            StdDraw.show();
            StdDraw.pause(10);

            time += dt;
        }

        StdAudio.close();
        System.exit(0);

    }
}
