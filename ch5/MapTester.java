import java.util.*;

public class MapTester {

    public static void main(String[] args) {
        Map<String, Integer> counts = new HashMap<>();

        counts.put("fred", 5);
        counts.put("george", 10);

        System.out.println(counts.get("fred"));
        System.out.println(counts.get("none"));
    }
}