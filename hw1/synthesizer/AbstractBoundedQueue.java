package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    // Getters for private fields
    public int capacity() { return capacity; }
    public int fillCount() { return fillCount; }
}
