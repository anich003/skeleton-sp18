public class Sort {

    /** Identifies the index of the smallest item in an String[] array
     * after startIndex.
     * @param input String[] array to be searched
     * @param startIndex 
     */
    public static int findSmallestIndex(String[] input, int startIndex) {
        int smallestIndex = startIndex;
        for (int i = smallestIndex; i < input.length; i++ ) {
            if (input[i].compareTo(input[smallestIndex]) < 0) {
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    /** Swaps two items in a String[] array */
    public static void swap(String[] input, int i, int j) {
        String tmp = input[i];
        input[i] = input[j];
        input[j] = tmp;
    }

    /** Recursive helper method for sorting an array starting at startIndex.
     * Invariant: Items before startIndex are in sorted order.
     * @param arr Array to be sorted
     * @param startIndex 
     */
    private static void sort(String[] arr, int startIndex) {
        if (startIndex != arr.length - 1) {
            int smallestIndex = findSmallestIndex(arr, startIndex);
            swap(arr, startIndex, smallestIndex);
            sort(arr, startIndex+1);
        }
    }

    /** Sorts the input String[] array using Selection-Sort */
    public static void sort(String[] arr) {
        sort(arr, 0);
    }
}
