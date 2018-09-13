public class Planet {
    /** Gravitational Constant in Newtons * m^2 / kg^2 */
    public static final double G = 6.67e-11;

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
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

    public double calcDistance(Planet p) {
        double dx = xxPos - p.xxPos;
        double dy = yyPos - p.yyPos;

        return Math.sqrt(dx*dx + dy*dy);
    }

    public double calcForceExertedBy(Planet p) {
        double r = calcDistance(p);
        return Planet.G * mass * p.mass / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - xxPos;
        double F  = calcForceExertedBy(p);
        double r  = calcDistance(p);

        return F * dx / r;
    }
    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - yyPos;
        double F = calcForceExertedBy(p);
        double r = calcDistance(p);

        return F * dy / r;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double Fx = 0;
        for (Planet p : planets) {
            if (!this.equals(p))
                Fx += calcForceExertedByX(p);
        }
        return Fx;
    }
    public double calcNetForceExertedByY(Planet[] planets) {
        double Fy = 0;
        for (Planet p : planets) {
            if (!this.equals(p))
                Fy += calcForceExertedByY(p);
        }
        return Fy;
    }
}
