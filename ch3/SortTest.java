import org.junit.Test;
import static org.junit.Assert.*;

public class SortTest {
    
    @Test
    public void testFindSmallestIndex() {
        String[] input = {"i", "have", "an", "egg"};
        int expected = 2;
        int output = Sort.findSmallestIndex(input, 0);
        assertEquals(expected, output);
    }

    @Test
    public void testSwap() {
        String[] input = {"i", "have", "an", "egg"};
        Sort.swap(input,2,3);
        String[] expected = {"i", "have", "egg", "an"};
        assertArrayEquals(expected, input);
    }

    @Test
    public void testSort() {
        String[] input = {"i", "have", "an", "egg"};
        String[] expected = {"an", "egg", "have", "i"};
        Sort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testSort2() {
        String[] input = {"There", "were", "TWO", "werewolves","in","bed"};
        String[] expected = {"TWO","There","bed","in","were","werewolves"};
        Sort.sort(input);
        assertArrayEquals(expected, input);
    }
}
