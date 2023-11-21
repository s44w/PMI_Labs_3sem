package functions;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;
import javax.xml.crypto.NoSuchMechanismException;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class TabulatedFunctions {
    private TabulatedFunctions() {}

    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory) {
        TabulatedFunctions.factory = factory;
    }
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointCount) {
        try {
            return factory.createTabulatedFunction(leftX, rightX, pointCount);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        try {
            return factory.createTabulatedFunction(leftX, rightX, values);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        try {
            return factory.createTabulatedFunction(points);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, double leftX, double rightX, int amount) {
        Constructor[] constructors = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] types = constructor.getParameterTypes();
            if (types.length == 3 && types[0].equals(Double.TYPE) && types[1].equals(Double.TYPE) && types[2].equals(Integer.TYPE)) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, amount);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return null;
    }
    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, double leftX, double rightX, double[] points) {
        Constructor[] constructors = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] types = constructor.getParameterTypes();
            if (types.length == 3 && types[0].equals(Double.TYPE) && types[1].equals(Double.TYPE) && types[2].equals(points.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, points);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return null;
    }
    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, FunctionPoint[] points) {
        Constructor[] constructors = functionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] types = constructor.getParameterTypes();
            if (types.length > 0 && types[0].equals(points.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(new Object[]{points});
                } catch (Exception e) {
                    System.out.println(e.getMessage() + '\n' + '\n');
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return null;
    }

    public static TabulatedFunction tabulate(Function f, double leftX, double rightX, int actualLength)
        throws FunctionPointIndexOutOfBoundsException{
        try {
            if (leftX < f.getLeftDomainBorder() || rightX > f.getRightDomainBorder()) {
                throw new IllegalArgumentException();
            }
            FunctionPoint[] points = new FunctionPoint[actualLength];

            double deltaX = (rightX - leftX) / (actualLength -1);
            for (int i = 0; i < actualLength; ++i) {
                points[i] = new FunctionPoint(leftX + i * deltaX, f.getFunctionValue(leftX + i * deltaX));
            }
            return new ArrayTabulatedFunction(points);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static void outputTabulatedFunction(TabulatedFunction f, OutputStream out) throws IOException, FunctionPointIndexOutOfBoundsException {
        DataOutputStream output = new DataOutputStream(out);
        output.writeInt(f.getActualLength());
        for (int i = 0; i < f.getActualLength(); ++i) {
            //output.writeChars("x: ");
            output.writeDouble(f.getPointX(i));
            //output.writeChars(" | y: ");
            output.writeDouble(f.getPointY(i));
        }
        output.flush();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream input = new DataInputStream(in);
        int amount = input.readInt();
        FunctionPoint[] points = new FunctionPoint[amount];
        for (int i = 0; i < amount; ++i) {
            points[i] = new FunctionPoint(input.readDouble(), input.readDouble());
        }
        return new ArrayTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction f, Writer out) throws IOException, FunctionPointIndexOutOfBoundsException {
        PrintWriter output = new PrintWriter(out);
        output.println(f.getActualLength());
        for (int i = 0; i < f.getActualLength(); ++i) {
            output.printf("x: %f y: %f\n", f.getPointX(i), f.getPointY(i));
        }
        output.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader in) throws IOException, ParseException {
        int actualLength = Integer.valueOf(in.readLine());
        FunctionPoint[] points = new FunctionPoint[actualLength];

        for (int i = 0; i < actualLength; ++i) {
            String[] values = in.readLine().split(" ");
            //"x: *val* y: *val*"
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            Number num = format.parse(values[1]);
            double x_val = num.doubleValue();

            num = format.parse(values[3]);
            double y_val = num.doubleValue();
            points[i] = new FunctionPoint(x_val, y_val);
        }
        return new ArrayTabulatedFunction(points);
    }
}