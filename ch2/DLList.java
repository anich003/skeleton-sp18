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
        sentinel.next = new Node(i, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    public DLList() {
        sentinel = new Node(null, null, null);
        size = 0;
    }

    public void addFirst(T i) {
        Node tmp = new Node(i, sentinel, sentinel.next);
        sentinel.next.prev = tmp;
        sentinel.next = tmp;
        size += 1;
    }

    public T getFirst() {
        return sentinel.next.item;
    }

    public T removeFirst() {
        Node tmp = sentinel.next;
        sentinel.next = tmp.next;
        tmp.next.prev = sentinel;
        size -= 1;
        return tmp.item;
    }

    public void addLast(T i) {
        Node tmp = new Node(i, sentinel.prev, sentinel);

        /** the current last node points to new Node */
        sentinel.prev.next = tmp;

        /** sentinel now points at new Node */
        sentinel.prev = tmp;

        size += 1;
    }

    public T getLast() {
        return sentinel.prev.item;
    }

    public T removeLast() {
        if (size == 0) return null;
        Node tmp = sentinel.prev;
        sentinel.prev = tmp.prev;
        tmp.prev.next = sentinel;
        size -= 1;

        return tmp.item;
    }

    public int size() {
        return size;
    }

    public String toString() {
        String res = "[";
        Node p = sentinel.next;
        while (p.next != sentinel) {
            res += p.item + ", ";
            p = p.next;
        }
        res += p.item;
        res += "]";
        return res;
    }

    public static void main(String[] args) {
        DLList<Integer> D = new DLList<>(5);
        System.out.println(D);
        System.out.println(D.size());
        D.removeFirst();
        System.out.println(D.size());
        D.addFirst(10);
        D.addFirst(15);
        System.out.println(D.size());
        System.out.println(D.getFirst());
        D.addLast(20);
        System.out.println(D.size());
        System.out.println(D);
        System.out.println(D.getLast());
        System.out.println(D.removeLast());
        System.out.println(D);
        D.addLast(30);
        System.out.println(D);
        System.out.println(D.size());
        D.addFirst(35);
        System.out.println(D.size() + " -> " + D);
    }
}
