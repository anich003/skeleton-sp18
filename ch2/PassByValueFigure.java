public class PassByValueFigure {

    public static void main(String[] args) {
        Walrus walrus = new Walrus(3500, 10.5);
        int x = 9;

        doStuff(walrus, x);
        System.out.println(walrus);
        System.out.println(x);
    }

    public static void doStuff(Walrus w, int x) {

        w.weight = w.weight - 100;
        x = x - 5;
    }
}
