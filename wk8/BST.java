public class BST<K, V> {
    private BST left;
    private BST right;
    private K key;
    private V value;

    public BST(K key) {
        left = null;
        right = null;
        this.key = key;
        this.value = null;
    }

    public K label() {
        return key;
    }

    public V value() {
        return value;
    }

    private void rotateRight() {}
    private void rotateLeft() {}

    public static void main(String[] args) {
        BST<Integer, Integer> T = new BST(7);
    }
}