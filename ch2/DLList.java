public class DLList<T> {
    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private int size;
    private Node sentinel;

    public DLList(T i) {
        sentinel = new Node(null, null, null);
        sentinel.next = new Node(i, null, null);
    }

    public static void main (String[] args) {
        DLList<Integer> D = new DLList<>(5);
    }
}
