import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayMap {

    @Test
    public void testArrayMapInit() {
        Map61B<String, Integer> am = new ArrayMap<>();
    }

    @Test
    public void testContainsKnownKeyReturnsTrue() {
        Map61B<String, Integer> am = new ArrayMap<>();
        am.put("fred", 5);
        assertTrue(am.containsKey("fred"));
        assertFalse(am.containsKey("george"));        
    }
}