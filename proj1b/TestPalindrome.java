import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static OffByOne obo = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testNonPalindromesAreFalse() {
        assertFalse(palindrome.isPalindrome("ab"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("furs"));
    }

    @Test
    public void testPalindromesAreTrue() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("12321"));
        assertTrue(palindrome.isPalindrome("racecar"));
    }

    @Test
    public void testOffByOnePalindrome() {
        assertTrue(palindrome.isPalindrome("a", obo));
        assertTrue(palindrome.isPalindrome("ab", obo));
        assertTrue(palindrome.isPalindrome("abb", obo));
        assertTrue(palindrome.isPalindrome("nopm", obo));
        assertTrue(palindrome.isPalindrome("12332", obo));
        assertTrue(palindrome.isPalindrome("racedbs", obo));    
    }
}
