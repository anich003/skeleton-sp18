public class SLList {
    private static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }
    /** The first item, if it exists is at sentinel.next */
    private IntNode sentinel;
    private int size;

    /** NOTE: By placing SLList in front of IntNode, we can store 
      * metadata about the datastructure. In this case, we are
      * storing the size of the list, seperate from the list itself
      * making it more robust to changes to the underlying data.
      */

    public SLList(int x) {
        sentinel = new IntNode(63, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    public SLList() {
        sentinel = new IntNode(47, null);
        size = 0;
    }

    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }

    public int getFirst() {
        return sentinel.next.item ;
    }

    public void addLast(int x) {
        IntNode p = sentinel;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    /** Private static helper method - does not use any instance variables */
    private static int size(IntNode n) {
        if (n.next == null) {
            return 1;
        }
        return 1 + size(n.next);
    }
    public int size () { 
        /** Commented once size became a caches instance variable */
        // return size(first); 
        return size;
    }

    public static void main(String[] args) {
        SLList L = new SLList();
        System.out.println(L.size());
        L.addLast(2);
        System.out.println(L.getFirst());
        L.addFirst(10);
        System.out.println(L.getFirst());
        L.addLast(1);
        System.out.println(L.size());
    }
}
