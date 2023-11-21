import functions.*;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        double leftBorder = 1;
        double rightBorder = 8;

        double[] values = {3, 5, 7, 9, 11, 13, 15};
        //y = 2x + 1
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(leftBorder, rightBorder, values);

        System.out.println("Test Function Values");
        DecimalFormat df = new DecimalFormat("#.##");
        try {
            for (double i = 1.0; i < 9.0; i += 1.5) {
                System.out.println("at index " + i + ": " + Double.valueOf(df.format(func.getFunctionValue(i))));
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        double x = 2.5, y = 6;
        System.out.println("\nTest adding new point x = " + x + "; y = " + y);
        System.out.println("Size before adding: " + func.getActualLength());

        func.addPoint(new FunctionPoint(x, y));

        System.out.println("Test function value at point x after adding it: " + func.getFunctionValue(x) );
        System.out.println("Size after adding: " + func.getActualLength());

        int index = 3;
        System.out.println("\nTest deleting point");
        System.out.println("Size before deleting: " + func.getActualLength());
        try {
            System.out.println("Value of a point with index " + index + " to be deleted: " + func.getPointY(index));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        try {
            func.deletePoint(index);
        }
        catch (functions.FunctionPointIndexOutOfBoundsException ex){
            ///idk
        }
        System.out.println("Size after deleting: " + func.getActualLength());

        try {
            System.out.println("Value of a point with same index after deleting: " + func.getPointY(3));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        //y = 2x + 1
        LinkedListTabulatedFunction funcLinked = new LinkedListTabulatedFunction(leftBorder, rightBorder, values);


        System.out.println("\n--------------- Linked List ---------------------\n");


        System.out.println("Test Function Values of Linked List");
        for (double i = 1; i < 8; i+=1.5){
            try {
                System.out.println("at index " + i + ": " + Double.valueOf(df.format(funcLinked.getFunctionValue(i))));
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        System.out.println("\nTest adding new point x = " + x + "; y = " + y);
        System.out.println("Size before adding: " + funcLinked.getActualLength());

        try {
            funcLinked.addPoint(new FunctionPoint(x, y));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println("Test linked list function value at point x after adding it: " + funcLinked.getFunctionValue(x));
            System.out.println("Size after adding: " + funcLinked.getActualLength());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("\nTest deleting point");
            System.out.println("Size before deleting: " + funcLinked.getActualLength());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println("Value of a point with index " + index + " to be deleted: " + funcLinked.getPointY(index));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            funcLinked.deletePoint(index);
        }
        catch (functions.FunctionPointIndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("Size after deleting: " + funcLinked.getActualLength());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println("Value of a point with same index after deleting: " + funcLinked.getPointY(3));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }
}