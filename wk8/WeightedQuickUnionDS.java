public class WeightedQuickUnionDS implements DisjointSets {
    private int[] parent;
    private int[] size;

    public WeightedQuickUnionDS(int N) {
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    private int root(int i) {
        return parent[i] == i ? i : parent[i];
    }

    public boolean isConnected(int p, int q)  {
        return root(p) == root(q);
    }

    /** Connect smaller tree to larger tree */
    public void connect(int p, int q) {
        int rootp = root(p);
        int rootq = root(q);
        if (rootp == rootq) return;
        if (size[rootp] < size[rootq]) {
            parent[rootp] = parent[rootq]; size[rootq] += size[rootp]; 
        } else {
            parent[rootq] = parent[rootp]; size[rootp] += size[rootq];
        }
    }
}