import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.beans.Transient;

public class ArrayDequeTest {
    private ArrayDeque<Integer> A;

    @Test
    public void testArrayDequeInit() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
    }

    @Before
    public void InitArray() {
        A = new ArrayDeque<>();
        assertEquals(0, A.size());
    }

    @Test
    public void testAddFirst() {
        A.addFirst(10);
        assertEquals(1, A.size());
        A.addFirst(15);
        assertEquals(2, A.size());
    }

    @Test
    public void testAddLast() {
        A.addLast(10);
        assertEquals(1,A.size());
        A.addLast(15);
        assertEquals(2,A.size());
    }

    @Test
    public void testAddFirstLastAndRemove() {
        A.addFirst(10);
        A.addLast(15);
        assertEquals(A.size(), 2);
        int tmp = A.removeFirst();
        assertEquals(10, tmp);
        assertEquals(A.size(), 1);
        tmp = A.removeFirst();
        assertEquals(15, tmp);
        assertEquals(A.size(), 0);
    }

    @Test
    public void testAddHundredItemsAndRemove() {
        for(int i = 0; i < 100; i++) {
            A.addFirst(i);
        }
        while (!A.isEmpty()) {
            A.removeFirst();
        }    
    }

    @Test
    public void testAddFirstLastAlternateAndRemove() {
        for (int i = 0; i < 100; i++) {
            if (i%2==0) {
                A.addFirst(i);
            } else {
                A.addLast(i);
            }
        }
        while (!A.isEmpty()) {
            A.removeFirst();
            A.removeLast();
        }
    }

    @Test
    public void testGetWithoutResizing() {
        A.addFirst(0);
        A.addFirst(1);
        A.addFirst(2);
        
        assertEquals(0, (long) A.get(2));
        assertEquals(1, (long) A.get(1));
        assertEquals(2, (long) A.get(0));
    }
    
    @Test
    public void testGetWithSizingUp() {
        for (int i = 0; i < 10; i++) {
            A.addFirst(i);
        }
        
        for (int i = 9; i >= 0; i--) {
            assertEquals(i, (long) A.get((9-i)));
        }
    }

    @Test
    public void testGetWithSizingUpAndDown() {
        // Initial resize should cause array to go up to 16...
        for (int i = 0; i < 10; i++) {
            A.addFirst(i);
        }
        // ..under 4 should cause array to shrink back down to 8
        while (A.size() > 4) {
            A.removeFirst();
        }
        assertEquals(0, (long) A.get(3));
        assertEquals(1, (long) A.get(2));
        assertEquals(2, (long) A.get(1));
        assertEquals(3, (long) A.get(0));
    }
}