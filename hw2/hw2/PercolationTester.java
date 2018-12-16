package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

public class PercolationTester {
    PercolationFactory pf = new PercolationFactory();
    @Test
    public void TestPercolationFactory () {
        Percolation p = pf.make(10);
    }

    @Test
    public void TestPercolationTopRowIsFullOnInititialization () {
        Percolation p = pf.make(10);
        for (int col=0; col < 10; col++) {
            assertTrue  ( p.isFull(0, col));
            assertFalse ( p.isFull(1, col));
        }
    }

    @Test
    public void TestPercolationNoneOpenOnInitialize () {
        int size = 5;
        Percolation p = pf.make(size);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                assertFalse(p.isOpen(row, col));
            }
        }
    }

    @Test
    public void TestDoesPercolate () {
        Percolation p = pf.make(5);
        assertFalse(p.percolates());
        for (int row = 0; row < 5; row++)
            p.open(row, 0);
        assertTrue(p.percolates());
    }

    @Test
    public void TestAlmostPercolates () {
        Percolation p = pf.make(5);
        assertFalse(p.percolates());
        for (int row = 0; row < 4; row++)
            p.open(row, 0);
        assertFalse(p.percolates());
    }
}
