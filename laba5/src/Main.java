import functions.*;

public class Main {
    public static void main(String[] args) {
        double[] arr = {1., 2., 3., 4., 5., 6., 7., 8., 9., 10.};
        double[] arr2 = {1, 2., 3., 4., 5., 6., 7., 8., 9.01, 10.};

        try {
            ArrayTabulatedFunction arrayFunc1 = new ArrayTabulatedFunction(1, 10, arr);
            ArrayTabulatedFunction arrayFunc2 = new ArrayTabulatedFunction(1, 10, arr2);
            LinkedListTabulatedFunction listFunc1 = new LinkedListTabulatedFunction(1, 10, arr);
            LinkedListTabulatedFunction listFunc2 = new LinkedListTabulatedFunction(1, 9, arr);
            ArrayTabulatedFunction arrayCloned1 = (ArrayTabulatedFunction) arrayFunc1.clone();

            System.out.println("ArrayTabulatedFunction:\n" + arrayFunc1.toString());
            System.out.println("Second ArrayTabulatedFunction:\n" + arrayFunc2.toString());

            System.out.println("LinkedListTabulatedFunction:\n" + listFunc1.toString());
            System.out.println("Second LinkedListTabulatedFunction:\n" + listFunc2.toString());


            System.out.println("\n\nDoes ArrayTabulatedFunction equal LinkedListTabulatedFunction?\n" + arrayFunc1.equals(listFunc1));
            System.out.println("Does LinkedListTabulatedFunction equal ArrayTabulatedFunction?\n" + listFunc1.equals(arrayFunc1));


            System.out.println("Does ArrayTabulatedFunction equal Second LinkedListTabulatedFunction?\n" + arrayFunc1.equals(listFunc2));
            System.out.println("Does Second LinkedListTabulatedFunction equal ArrayTabulatedFunction?\n" + listFunc2.equals(arrayFunc1));

            System.out.println("\n\nArray`s hashCode: " + arrayFunc1.hashCode());
            System.out.println("LinkedList`s hashCode: " + listFunc1.hashCode());
            System.out.println("Second LinkedList`s hashCode: " + listFunc2.hashCode());
            System.out.println("Changed Array`s x9, function`s hashcode:  " + arrayFunc2.hashCode());

            System.out.println("\n\nCloned Array function after deleting Array`s point: ");
            arrayFunc1.deletePoint(2);
            System.out.println("ArrayTabulatedFunction:\n" + arrayFunc1.toString());
            System.out.println("Cloned ArrayTabulatedFunction:\n" + arrayCloned1.toString());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}