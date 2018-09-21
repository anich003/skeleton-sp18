public class Sort {

    public static int findSmallestIndex(String[] input) {
        int smallestIndex = 0;
        for (int i = 0; i < input.length; i++ ) {
            if (input[i].compareTo(input[smallestIndex]) < 0) {
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    public static void sort(String[] arr) {
        // Find smallest
        // Swap itmes
        // Recurse
    }
}
