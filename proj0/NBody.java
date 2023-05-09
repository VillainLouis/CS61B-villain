public class NBody {
    public static void main(String[] args) {
        // Collecting all needed input.
        double T = Double.valueOf(args[0]);
        double dt = Double.valueOf(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);

        // Drawing the background.
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        String background = "images/starfield.jpg";

        StdDraw.enableDoubleBuffering();

        double t = 0;
        final int n = planets.length;
        while (t <= T) {
            double[] xForces = new double[n];
            double[] yForces = new double[n];
            for (int i = 0; i < n; ++i) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int i = 0; i < n; ++i) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            // background
            StdDraw.picture(0, 0, background); //, radius, radius);

            // all planets
            for (Planet planet : planets) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }

        // Textual output from filename
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }

    /** Return a double corresponding to the radius of the universe in the given file*/
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        int firstItem = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /** Return an array of Planets corresponding the the planets in the given file*/
    public  static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int N = in.readInt();
        double r = in.readDouble();
        Planet[] planets = new Planet[N];
        for (int i = 0; i < N; ++i) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xP,yP,xV,yV,m,img);
        }
        return planets;
    }
}