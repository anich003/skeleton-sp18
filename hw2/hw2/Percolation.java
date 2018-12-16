package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import static org.junit.Assert.*;
import org.junit.Test;

public class Percolation {
    private int size;
    private WeightedQuickUnionUF uf;
    private boolean[] openSites;
    private int numOpenSites;

    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException("N must be greater than 0");
        this.size = N;
        int numSites = N * N + 2;
        this.numOpenSites = 0;
        this.openSites = new boolean[numSites];
        this.openSites[0] = true;
        this.openSites[numSites - 1] = true;
        this.uf = new WeightedQuickUnionUF(numSites);
        for (int col = 0; col < N; col++) {
            this.uf.union(0, XYTo1D(0, col));
            this.uf.union(numSites - 1, XYTo1D(N - 1, col));
        }
    }

    /**
     * Translates the XY coordinate to an internal representation of available sites
     * @param x Integer between 0 and N-1
     * @param y Integer between 0 and N-1
     * @return Integer corresponding to internal union-find structure
     */
    private int XYTo1D(int x, int y) {
        return 1 + (x * this.size) + y;
    }

    private void validateIndices(int row, int col) {
        if (row < 0 || row > this.size - 1 || col < 0 || col > this.size - 1)
            throw new IndexOutOfBoundsException("Row and Column indices must be between 0 and N-1");
    }

    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return this.isOpen(row,col) && this.uf.connected(0, XYTo1D(row,col));
    }

    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        int index = XYTo1D(row,col);
        return this.openSites[index];
    }

    private void connect(int row1, int col1, int row2, int col2) {
        if (       row2 < 0
                || row2 > this.size - 1
                || col2 < 0
                || col2 > this.size -1
                || !isOpen(row2, col2)
        ) return;

        this.uf.union(XYTo1D(row1, col1), XYTo1D(row2, col2));
    }

    public void open(int row, int col) {
        validateIndices(row, col);
        if (!isOpen(row,col)) {
            this.openSites[XYTo1D(row, col)] = true;
            this.numOpenSites += 1;
            connect(row, col, row - 1, col);
            connect(row, col, row + 1, col);
            connect(row, col, row, col - 1);
            connect(row, col, row, col + 1);
        }
    }

    public boolean percolates () {
        return this.uf.connected(0, XYTo1D(this.size - 1, this.size - 1) + 1);
    }

    public int numberOfOpenSites () {
        return this.numOpenSites;
    }
}
