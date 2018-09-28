package synthesizer;

import synthesizer.*;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
    }

    @Test
    public void testEnqueueOneItem() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(5);
    }

    @Test
    public void testEnqueueAndDeque() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(5);
        int expected = 5;
        assertEquals((Integer) expected, arb.dequeue());
    }

    @Test(expected=RuntimeException.class)
    public void testDequeueEmptyThrowsError() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.dequeue();
    }

    @Test(expected=RuntimeException.class)
    public void testEnqueueFullThrowsError() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4); // should throw excpetion
    }

    @Test
    public void testCanEnqueueDequeue() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        for (int i = 0; i < 4; i++) {
            arb.enqueue(i);
        }
        for (int j = 0; j < 100; j++) {
            arb.enqueue(arb.dequeue());
        }
    }

    @Test
    public void testCanEnqueueUntilFull() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        while (!arb.isFull()) {
            arb.enqueue(0);
        }
        int expected = 4;
        assertEquals((Integer) expected, (Integer) arb.fillCount());
        assertTrue(arb.isFull());
    }

    /** Calls tests for ArrayRingBuffer.  
    public static void main(String[] args) {
        //jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
    */
} 
