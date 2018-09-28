public interface Map61B<K, V> {
    void put(K key, V value);
    boolean containsKey(K key);
    V get(K key);
    K[] keys();
    int size();
}