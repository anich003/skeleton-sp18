package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /** Helper methods that moves an index around a cyclic array.
     *
     */
    private int plusOne(int dex) {
        int newDex = dex + 1;
        return newDex <= capacity - 1 ? newDex : 0;
    }
    private int minusOne(int dex) {
        int newDex = dex - 1;
        return newDex >= 0 ? newDex : capacity - 1;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) { throw new RuntimeException("Ring buffer overflow"); }
        rb[last] = x;
        last = plusOne(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) { throw new RuntimeException("Cannot dequeue from empty RingBuffer"); }
        T tmp = rb[first];
        first = plusOne(first);
        fillCount -= 1;
        return tmp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.

    public static void main(String[] args) {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(5);
        System.out.println(arb.peek());
        System.out.println(arb.dequeue());
    }
}
