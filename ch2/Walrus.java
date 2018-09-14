public class Walrus {
    public double weight;
    public double tuskSize;

    public Walrus(double w, double ts) {
        weight = w;
        tuskSize = ts;
    }

    @Override
    public String toString() {
        return "weight: " + weight + ", tuskSize: " + tuskSize;
    }
}
