import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator obo = new OffByOne();

    @Test
    public void testEqualCharsReturnTrue() {
        assertTrue(obo.equalChars('a','b'));
        assertTrue(obo.equalChars('b','a'));
        assertTrue(obo.equalChars('J','K'));
        assertTrue(obo.equalChars('K','J'));
        assertTrue(obo.equalChars('K','J'));
        assertTrue(obo.equalChars('Z','['));
        assertTrue(obo.equalChars('`','a'));
        assertTrue(obo.equalChars('n','m'));
        assertTrue(obo.equalChars('o','p'));
    }

    @Test
    public void testEqualCharsReturnsFalse() {
        assertFalse(obo.equalChars('a','B'));
        assertFalse(obo.equalChars('A','b'));
        assertFalse(obo.equalChars('A','z'));
    }
    
}
