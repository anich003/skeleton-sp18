import java.util.*;

/** Implementation of Map interface using Arrays as underlying data store */
public class ArrayMap<K, V> implements Map61B<K, V> {

    private K[] keys;
    private V[] values;
    private int size;
    
    public ArrayMap() {
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size = 0;
    }

    /** Returns index that key currently resides in. If key not found in map, returns -1 */
    private int getKeyIndex(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /** Checks underlying keys array if key is in list. If so, returns true, otherwise false */
    @Override
    public boolean containsKey(K key) {
        return getKeyIndex(key) > 0;
    }

    /** Sets the value at the index key to value. If a value already exists, then overwrites existing */
    @Override
    public void put(K key, V value) {
        int dex = getKeyIndex(key);
        if (dex == -1) {
            keys[size] = key;
            values[size] = value;
            size += 1;
        } else {
            values[dex] = value;
        }
    }


    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public K[] keys() {
        return null;
    }

    @Override
    public int size() { return size; }
}