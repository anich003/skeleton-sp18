/** Double ended queue implemented with doubly-linked lists
 *
 */
public class LinkedListDeque<Item> implements Deque<Item>{
    private class Node {
        Node prev;
        Item item;
        Node next;

        public Node(Item i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque(Item i) {
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

    /** Adds an item of type Item front of deque
     * Does not use any looping or recursion. Takes O(1) time.
     * @param item
     */
    @Override
    public void addFirst(Item item) {
        Node tmp = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = tmp;
        sentinel.next = tmp;
        size += 1;
    }

    /** Adds an item of Type Item to back of deque.
     * Does not use any looping or recusion. Takes O(1) time.
     * @param item
     */
    @Override
    public void addLast(Item item) {
        Node tmp = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = tmp;
        sentinel.prev = tmp;
        size += 1;
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

    /** Prints the items in the deque from first to last
     * seperated by a space.
     */
    @Override
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
     * @return Item item or null
     */
    @Override
    public Item removeFirst() {
        if (isEmpty()) return null;
        Node tmp = sentinel.next;
        sentinel.next = tmp.next;
        tmp.next.prev = sentinel;
        size -= 1;
        return tmp.item;
    }

    /** Removes and returns first item at back of deque.
     * If no such item exists return null
     * @return Item item or null
     */
    @Override
    public Item removeLast() {
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
    @Override
    public Item get(int index) {
        if (index > (size - 1)) return null;
        Node p = sentinel.next;
        int count = 0;
        while (count != index) {
            p = p.next;
        }
        return p.item;
    }

    /** Helper function to public getRecursive. */
    private Item getRecursive(Node p, int index) {
        return index == 0 
            ? p.item 
            : index >= size 
                ? null 
                : getRecursive(p.next, index - 1);
    }
    
    /** Gets item at index, recursively, where 0 is the front,  
     * 1 is the next item and so forth. If no such item exists,
     * returns null. Does not alter deque.
     * @param index
     * @return
    */
    @Override
    public Item getRecursive(int index) {
        return getRecursive(sentinel.next, index);
    }

    public static void main(String[] args) {
        LinkedListDeque<String> lld = new LinkedListDeque<String>();
        lld.addFirst("front");
        lld.addFirst("middle");
        lld.addFirst("middleBack");
        lld.addFirst("back");
        System.out.println(lld.getRecursive(0));
    }
}