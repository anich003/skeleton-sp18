/** Double ended queue implemented with doubly-linked lists
 *
 */
public class LinkedListDeque<T> {
    private class Node {
        Node prev;
        T item;
        Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque(T i) {
        sentinel = new Node(null, null, null);
        sentinel.next = new Node(i, sentinel, sentinel);
        size = 1;
    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Adds an item of type T front of deque
     * Does not use any looping or recursion. Takes O(1) time.
     * @param item
     */
    public void addFirst(T item) {
        Node tmp = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = tmp;
        sentinel.next = tmp;
        size += 1;
    }

    /** Adds an item of Type T to back of deque.
     * Does not use any looping or recusion. Takes O(1) time.
     * @param item
     */
    public void addLast(T item) {
        Node tmp = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = tmp;
        sentinel.prev = tmp;
        size += 1;
    }

    public boolean isEmpty() { return size == 0; }

    public int size() { return size; }

    /** Prints the items in the deque from first to last
     * seperated by a space.
     */
    public void printDeque() {
        if (isEmpty()) return;
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println("");
    }

    /** Removes and returns first item at the front of the deque.
     * If no such item exists return null
     * @return T item or null
     */
    public T removeFirst() {
        if (isEmpty()) return null;
        Node tmp = sentinel.next;
        sentinel.next = tmp.next;
        tmp.next.prev = sentinel;
        size -= 1;
        return tmp.item;
    }

    /** Removes and returns first item at back of deque.
     * If no such item exists return null
     * @return T item or null
     */
    public T removeLast() {
        if (isEmpty()) return null;
        Node tmp = sentinel.prev;
        sentinel.prev = tmp.prev;
        tmp.prev.next = sentinel;
        size -= 1;
        return tmp.item;
    }

    /** Gets the item at the index, where 0 is the front, 1
     * is the next item, and so forth. If no such item exists,
     * returns null. Does not alter the deque. Uses iteration.
     * @param index
     * @return
     */
    public T get(int index) {
        if (index > (size - 1)) return null;
        Node p = sentinel.next;
        int count = 0;
        while (count != index) {
            p = p.next;
        }
        return p.item;
    }

    public static void main(String[] args) {
        LinkedListDeque<String> lld = new LinkedListDeque<String>();
        lld.addFirst("front");
        lld.printDeque();
    }
}