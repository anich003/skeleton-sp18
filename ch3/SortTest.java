public class SortTest {
    
    public static void testFindSmallestIndex() {
        String[] input = {"i", "have", "an", "egg"};
        int expected = 2;
        int output = Sort.findSmallestIndex(input);
        org.junit.Assert.assertEquals(expected, output);
        System.out.println("testFindSmallestIndex() : PASSED");
    }

    /**
    public static void testSort() {
        String[] input = {"i", "have", "an", "egg"};
        String[] expected = {"an", "egg", "have", "i"};
        Sort.sort(input);
        org.junit.Assert.assertArrayEquals(expected, input);
    }
    */

    public static void main(String[] args) {
        testFindSmallestIndex();
    }
}
