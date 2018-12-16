public interface DisjointSets {
    /* Connects two items P and Q. */
    void connect(int p, int q);

    /* Checks if two nodes are connected */
    boolean isConnected(int p, int q);
}