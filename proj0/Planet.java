public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double G = 6.67 * 1e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

//    private double getSquare(double d) {
//        return d * d;
//    }
    /** Calculates the distance between two Planets.*/
    public double calcDistance(Planet another) {
        return Math.pow(Math.pow(this.xxPos - another.xxPos, 2) + Math.pow(this.yyPos - another.yyPos, 2), 0.5);
    }

    /** Takes in a planet and returns a double describing the force exerted on this planet by the given planet.*/
    public double calcForceExertedBy(Planet another) {
        double r = this.calcDistance(another);
        return G * this.mass * another.mass / Math.pow(r, 2);
    }

    /** Discribe the force exerted in the X direction.*/
    public double calcForceExertedByX(Planet another) {
        double F = this.calcForceExertedBy(another);
        double r = this.calcDistance(another);
        double dx = another.xxPos - this.xxPos;
        return F * dx / r;
    }

    /** Discribe the force exerted in the Y direction.*/
    public double calcForceExertedByY(Planet another) {
        double F = this.calcForceExertedBy(another);
        double r = this.calcDistance(another);
        double dy = another.yyPos - this.yyPos;
        return F * dy / r;
    }

    /** Discribe the net force exerted in the X direction of all other planet.*/
    public double calcNetForceExertedByX(Planet[] planets) {
        double xForce = 0;
        for (Planet planet : planets) {
            if (this.equals(planet)) {
                continue;
            } else {
                xForce += calcForceExertedByX(planet);
            }
        }
        return xForce;
    }

    /** Discribe the net force exerted in the Y direction of all other planet.*/
    public double calcNetForceExertedByY(Planet[] planets) {
        double yForce = 0;
        for (Planet planet : planets) {
            if (this.equals(planet)) {
                continue;
            } else {
                yForce += calcForceExertedByY(planet);
            }
        }
        return yForce;
    }

    /** Update the planet's position and velocity*/
    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        xxVel += dt * aX;
        yyVel += dt * aY;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    /** Draw the planet at its proper position.*/
    public void draw() {
        StdDraw.picture(xxPos, yyPos ,"images/" + imgFileName);
    }
}