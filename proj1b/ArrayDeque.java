public class ArrayDeque<Item> implements Deque<Item>{
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int RFACTOR = 2;            // Resize Factor
    private static final double UFACTOR = 0.25;      // Usage Factor
    private static final int MINIMUM_ARRAY_SIZE = 8;

    public ArrayDeque() {
        items = (Item[]) new Object[MINIMUM_ARRAY_SIZE];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private int minusOne(int index) {
        int newIndex = index - 1;
        return newIndex >= 0 ? newIndex : items.length - 1;
    }

    private int plusOne(int index) {
        int newIndex = index + 1;
        return newIndex <= items.length - 1 ? newIndex : 0;
    }

    private void change_capacity(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];

        int srcIndex = plusOne(nextFirst);
        int dstIndex = 0;
        int count = 0;

        while (count < size) {
            newItems[dstIndex] = items[srcIndex];
            srcIndex = plusOne(srcIndex);
            dstIndex += 1; 
            count++;
        }
        
        items = newItems;
        /** This needs to come after items is reassigned so that it correctly
         * cycles back to the end of the new array, not the old one! */
        nextFirst = minusOne(0);
        nextLast = size;
    }

    /** Determines if array needs to be resized to either a larger or smaller array. */
    private void check_capacity_and_resize() {
        if (size == items.length) {
            change_capacity(items.length * RFACTOR);
        } 
        else if ((items.length > MINIMUM_ARRAY_SIZE) & ((double) size / items.length <= UFACTOR)) {
            change_capacity(items.length / 2);
        }
    }

    @Override
    public void addFirst(Item i) {
        check_capacity_and_resize();
        items[nextFirst] = i;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(Item i) {
        check_capacity_and_resize();
        items[nextLast] = i;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public Item getFirst() {
        return items[plusOne(nextFirst)];
    }

    public Item getLast() {
        return items[minusOne(nextLast)];
    }
    
    @Override
    public Item get(int index) {
        if (index > (size - 1)) throw new IndexOutOfBoundsException();
        int adjIndex = (plusOne(nextFirst) + index) % items.length;
        return items[adjIndex];
    }

    /** TODO Fix the null-ization of the trailing element. When removeFirst and removeLast are called
     * together, such as in Palindrome, and when the two pointers are adjacent in the array, then first call
     * will null out the value the second call is trying to retrieve.
     * 
     * Instead, only the value that is being retrieved should be null (NOT the trailing element)
     */
    @Override
    public Item removeFirst() {
        if (size == 0) { return null; }
        Item tmp = getFirst();
        nextFirst = plusOne(nextFirst);
        items[nextFirst] = null;
        size -= 1;
        check_capacity_and_resize();
        return tmp;
    }

    @Override
    public Item removeLast() {
        if (size == 0) { return null; }
        Item tmp = getLast();
        nextLast = minusOne(nextLast);
        items[nextLast] = null;
        size -= 1;
        check_capacity_and_resize();
        return tmp;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public void printDeque () {
        int count = 0;
        for (int i = plusOne(nextFirst); count < size; i = plusOne(i)) {
            System.out.print(items[i] + " ");
            count++;
        }
        System.out.println();
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < items.length; i++) {
            res += items[i] + " ";
        }
        return res;
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> A = new ArrayDeque<>();
    }
}