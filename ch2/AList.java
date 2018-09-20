public class AList<T> {
    private T[] items;
    private int size;
    private int RFACTOR; // Multiplicative Factor for resizing
    private int UFACTOR; // Usage Factor for resizing;

    public AList() {
        items = (T[]) new Object[100];
        size = 0;
    }

    public void addLast(T i) {
        if (size == items.length) {
            resize_array(size + 1);
        }
        items[size++] = i;
    }

    public T getLast() {
        return items[size - 1];
    }

    public T get(int i) {
        return items[i];
    }

    public int size() {
        return size;
    }

    public T removeLast() {
        return items[--size];
    }

    private void resize_array(int capacity) {
        T[] dst = (T[]) new Object[capacity];
        System.arraycopy(items,0,dst,0,size);
        items = dst;
    }

    public static void main(String[] args) {
        AList<Integer> A = new AList<>();
        for (int i = 0; i < 200; i++) {
            A.addLast(i);
            System.out.println(A.size());
        }
        System.out.println(A.removeLast());
    }
}