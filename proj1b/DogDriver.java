public class DogDriver {
    public static void main(String[] args) {
        Dog[] dogs = {new Dog(1), new Dog(2), new Dog(3)};
        System.out.println(dogs[1].size);

        Dog tmp = dogs[1];

        dogs[1] = null;

        System.out.println(tmp.size);
    }
}
