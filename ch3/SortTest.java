public class SortTest {
    
    public static void testFindSmallestIndex() {
        String[] input = {"i", "have", "an", "egg"};
        int expected = 2;
        int output = Sort.findSmallestIndex(input, 0);
        org.junit.Assert.assertEquals(expected, output);
        System.out.println("testFindSmallestIndex() : PASSED");
    }

    public static void testSwap() {
        String[] input = {"i", "have", "an", "egg"};
        Sort.swap(input,2,3);
        String[] expected = {"i", "have", "egg", "an"};
        org.junit.Assert.assertArrayEquals(expected, input);
        System.out.println("testSwap() : PASSED");
    }

    
    public static void testSort() {
        String[] input = {"i", "have", "an", "egg"};
        String[] expected = {"an", "egg", "have", "i"};
        Sort.sort(input);
        org.junit.Assert.assertArrayEquals(expected, input);
        System.out.println("testSort() : PASSED");
    }

    public static void testSort2() {
        String[] input = {"There", "were", "TWO", "werewolves","in","bed"};
        String[] expected = {"TWO","There","bed","in","were","werewolves"};
        Sort.sort(input);
        org.junit.Assert.assertArrayEquals(expected, input);
        System.out.println("testSort2() : PASSED");
    }
    
    public static void main(String[] args) {
        testFindSmallestIndex();
        testSwap();
        testSort();
        testSort2();
    }
}
