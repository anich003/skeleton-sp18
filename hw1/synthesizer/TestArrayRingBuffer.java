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
    public void TestEnqueueOneItem() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(5);
    }

    /** Calls tests for ArrayRingBuffer.  
    public static void main(String[] args) {
        //jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
    */
} 
