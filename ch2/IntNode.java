public class IntNode {
    public int item;
    public IntNode next;

    public IntNode(int x) {
        item = x;
        next = null;
    }

    public static void main(String[] args) {
        IntNode L = new IntNode(5);
    }
}