import functions.FunctionPoint;
import functions.TabulatedFunction;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        double leftBorder = 1;
        double rightBorder = 11;

        double[] values = {3, 5, 7, 9, 11};
        //y = 2x + 1
        TabulatedFunction func = new TabulatedFunction(leftBorder, rightBorder, values);

        System.out.println("Test Function Values");
        DecimalFormat df = new DecimalFormat("#,##");
        for (double i = Double.valueOf(df.format(3.3)); i < 15; i+=Double.valueOf(df.format(3.3))){
            System.out.println("at index " + Double.valueOf(df.format(i)) + ": " + func.getFunctionValue(Double.valueOf(df.format(i))));
        }

        double x = 2, y = 5;
        System.out.println("\nTest adding new point x = " + x + "; y = " + y);
        System.out.println("Size before adding: " + func.getActualLength());

        func.addPoint(new FunctionPoint(x, y));

        System.out.println("Test function value at point x after adding it: " + func.getFunctionValue(x) );
        System.out.println("Size after adding: " + func.getActualLength());

        int index = 3;
        System.out.println("\nTest deleting point");
        System.out.println("Size before deleting: " + func.getPointsCount());
        System.out.println("Value of a point with index " + index + " to be deleted: " + func.getPointY(index));
        func.deletePoint(index);
        System.out.println("Size after deleting: " + func.getPointsCount());
        System.out.println("Value of a point with same index after deleting: " + func.getPointY(3));
    }
}