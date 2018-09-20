public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int RFACTOR = 2;            // Resize Factor
    private double UFACTOR = 0.25;      // Usage Factor

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = items.length / 2;
        nextLast = nextFirst + 1;
    }

    private int minusOne(int index) {
        int newIndex = index - 1;
        return newIndex > 0 ? newIndex : items.length + newIndex;
    }

    private int plusOne(int index) {
        int newIndex = index + 1;
        return newIndex < items.length - 1 ? newIndex : newIndex - items.length;
    }

    private void check_capacity_and_resize() {
        if (size == items.length) {
            Item[] newItems = (Item[]) new Object[items.length * RFACTOR];
            // TODO 
        }
    }

    public void addFirst(Item i) {
        items[nextFirst] = i;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(Item i) {
        items[nextLast] = i;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public Item getFirst() {
        return items[nextFirst + 1];
    }

    public Item getLast() {
        return items[nextLast - 1];
    }

    public Item removeFirst() {
        if (size == 0) { return null; }
        Item tmp = getFirst();
        items[nextFirst] = null;
        nextFirst = plusOne(nextFirst);
        size -= 1;
        return tmp;
    }

    public Item removeLast() {
        if (size == 0) { return null; }
        Item tmp = getLast();
        items[nextLast] = null;
        nextLast = minusOne(nextLast);
        size -= 1;
        return tmp;
    }

    public int size() { return size; }

    public String toString() {
        String res = "";
        for (int i = 0; i < items.length; i++) {
            res += items[i] + " ";
        }
        return res;
    }
    public static void main(String[] args) {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        A.addFirst(10);
        A.addLast(15);
        System.out.println(A);
        System.out.println(A.size());
        System.out.println(A.removeFirst());
        System.out.println(A.size());
        System.out.println(A.removeLast());
        System.out.println(A.removeLast());
    }
}