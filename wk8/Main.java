public class Main {
    public static void main(String[] args) {
        DisjointSets qu = new WeightedQuickUnionDS(10);

        qu.connect(1, 2);
        qu.connect(2, 9);

        System.out.println(qu.isConnected(1, 2));
        System.out.println(qu.isConnected(2, 9));
        System.out.println(qu.isConnected(1, 9));
        System.out.println(qu.isConnected(5, 9));
    }
}