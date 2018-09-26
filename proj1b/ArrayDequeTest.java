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
    public void testAddUntilFirstResizeThenRemove() {
        String word = "aardvark";
        ArrayDeque<Character> B = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            B.addLast(word.charAt(i));
        }
        while (A.size() >= 1) {
            B.removeFirst();
            B.removeLast();
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

    /** Original implementation of removeFirst and removeLast nulled
     * the trailing element ie the item before/after the actual item 
     * being removed. This was because after saving a pointer to the 
     * desired object, setting the item[i] = null, causes java to set
     * the desired object to null. The function would then only return
     * null items. This was written this way to make sure there were
     * no loitering references to potentially large objects in the 
     * array.
     * 
     * For now (180926) the ArrayDeque now sets items[i] = null which
     * works fine for primitive types like int, char. We'll have to see
     * what happens with reference types. Expect those tests to fail.
     */
    @Test
    public void testRemoveFirstAndLastDontInterfere() {
        String word = "aardvark";
        ArrayDeque<Character> B = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            B.addLast(word.charAt(i));
        }
        while (A.size() >= 1) {
            A.removeFirst();
            A.removeLast();
        }
    }
}
