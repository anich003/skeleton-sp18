public class HoF {
    public static void main(String[] args) {
        /** An object that represents a function capable of higher order composition */
        TenX tenx = new TenX();
        Subtract15 subtract15 = new Subtract15();

        System.out.println(tenx.apply(tenx.apply(2)));
        System.out.println(subtract15.apply(tenx.apply(2)));
    }
}