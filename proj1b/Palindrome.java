public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        int numChars = word.length();
        ArrayDeque<Character> A = new ArrayDeque<>();
        for (int i = 0; i < numChars; i++) {
            A.addLast(word.charAt(i));
        }
        return A;
    }

    private boolean isPalindrome(Deque wordDeque) {
        if (wordDeque.size() <= 1) { return true; }
        return wordDeque.removeFirst() == wordDeque.removeLast() & isPalindrome(wordDeque);
    }

    private boolean isPalindrome(Deque wordDeque, CharacterComparator cc) {
        if (wordDeque.size() <= 1) { return true; }
        wordDeque.printDeque();
        char first = (char) wordDeque.removeFirst();
        char last = (char) wordDeque.removeLast();
        System.out.println("f: " + first + " l: " + last);
        return cc.equalChars(first, last) & isPalindrome(wordDeque, cc);
    }

    public boolean isPalindrome(String word) {
        return isPalindrome(wordToDeque(word));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }

    public static void main(String[] args) {
        Palindrome p = new Palindrome();
        Deque<Character> A = p.wordToDeque("aardvark");
        A.printDeque();
    }
}
