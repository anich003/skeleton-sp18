public class IntList {

    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return size of list using recursion */
    public int size () {
        if (rest == null) {
            return 1;
        } else {
            return rest.size() + 1;
        }
    }


    /** Return size of list using iteration*/
    public int iterativeSize () {
        IntList p = this;
        int count = 0;
        while (p != null) {
            count++;
            p = p.rest;
        }
        return count;
    }

    /** Return the ith item of the list using recursion */
    public int get(int i) {
        return i == 0 ? first : rest.get(i - 1);
    }

    /** Creates a new IntList with every element incremented by x */
    public static IntList incrList (IntList L, int x) {
        if (L.size() == 0) {
            return null;
        } else {
            IntList Q = new IntList(L.get(0) + x, null);
            int size = L.size();
            for (int i = 1; i < size; i++) {
                Q = new IntList(L.get(i) + x, Q);
            }
            return Q;
        }
    }

    public static IntList dincrList (IntList L, int x) {
        IntList p = L;
        while (p != null) {
            p.first += x;
            p = p.rest;
        }
        return L;
    }

    @Override
    public String toString () {
        IntList p = this;
        String result = "";
        result += "[";
        while (p.rest != null) {
            result += p.first;
            result += ", ";
            p = p.rest;
        }
        result += p.first;
        result += "]";
        return result;
    }

    public static void main (String[] args) {
        IntList L = new IntList(15, null);
        System.out.println(L.size());
        L = new IntList(10, L);
        L = new IntList(5, L);
        System.out.println(L.size());
        System.out.println(L.iterativeSize());
        System.out.println("L.get(1) = " + L.get(1));
        System.out.println("");
        System.out.println(dincrList(L,2));
        System.out.println(dincrList(L,2));
    }
}
