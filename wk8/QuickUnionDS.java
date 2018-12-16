public class QuickUnionDS implements DisjointSets {
    private int[] id;
    
    public QuickUnionDS(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    /** Tree can become imbalanced, and searching up for root can take a long time */

    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    public void connect(int p, int q) {
        id[root(p)] = root(q);
    }

    private int root(int i) {
        return id[i] == i ? i : root(id[i]);
    }
}