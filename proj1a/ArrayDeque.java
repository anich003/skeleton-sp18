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
        return newIndex >= 0 ? newIndex : items.length + newIndex;
    }

    private int plusOne(int index) {
        int newIndex = index + 1;
        return newIndex <= items.length - 1 ? newIndex : newIndex - items.length;
    }

    /** Calculate starting point in dst array when copying over from src array 
     * For example, when transitioning from an length 8 array to length 16,
     * the dst array should start at position 4
     * */
    private int calculate_offset(int lengthSrc, int lengthDst) {
        return (lengthDst - lengthSrc) / 2;
    }

    /** Determines if array needs to be resized to either a larger or smaller array.
     * If 
     * */
    private void check_capacity_and_resize() {
        if (size == items.length) {
            Item[] newItems = (Item[]) new Object[items.length * RFACTOR];
            int newNextFirst = calculate_offset(items.length, newItems.length);

            /** Copy over array starting from nextFirst to end... */
            System.arraycopy(items, nextFirst, newItems, newNextFirst, items.length - nextFirst);
            /** ...then copy over from start to nextFirst. */
            if (nextLast < nextFirst) {
                System.arraycopy(items, 0, newItems, items.length - nextFirst + newNextFirst, nextLast + 1);
            }
            
            nextFirst = newNextFirst - 1;
            nextLast = newNextFirst + items.length;
            items = newItems;
        }

        if (size / items.length < UFACTOR) {
            /** New smaller array */
            Item[] newItems = (Item[]) new Object[items.length / 2];
            
        }
    }

    public void addFirst(Item i) {
        check_capacity_and_resize();
        items[nextFirst] = i;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(Item i) {
        check_capacity_and_resize();
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
        check_capacity_and_resize();
        return tmp;
    }

    public Item removeLast() {
        if (size == 0) { return null; }
        Item tmp = getLast();
        items[nextLast] = null;
        nextLast = minusOne(nextLast);
        size -= 1;
        check_capacity_and_resize();
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
        for (int i = 0; i < 1000000; i++) {
            if (i%2 == 0) 
                A.addFirst(i);
            else
                A.addLast(i);
        }
        System.out.println(A.size());
    }
}
