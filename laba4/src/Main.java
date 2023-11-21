import functions.*;
import functions.basic.*;
import functions.meta.*;

import java.io.*;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        Sin sinFunc = new Sin();
        Cos cosFunc = new Cos();
        System.out.println("Basic sin and cos values:");
        for (double i = 0; i < 2*Math.PI; i+=0.1){
            System.out.printf("i: %f | sin(i): %f | cos(i): %f \n",
                    i, sinFunc.getFunctionValue(i), cosFunc.getFunctionValue(i));
        }
        TabulatedFunction sinTabul;
        TabulatedFunction cosTabul;

        try {
            sinTabul = TabulatedFunctions.tabulate(sinFunc, 0, 2 * Math.PI, 10);
            cosTabul = TabulatedFunctions.tabulate(cosFunc, 0, 2 * Math.PI, 10);

            System.out.println("\nTabulated sin and cos values:");
            for (double i = 0; i < 2 * Math.PI; i += 0.1) {
                System.out.printf("i: %f | sin(i): %f | cos(i): %f \n",
                        i, sinTabul.getFunctionValue(i), cosTabul.getFunctionValue(i));
            }


            Function sumFunc = Functions.sum(Functions.mult(sinTabul, sinTabul), Functions.mult(cosTabul, cosTabul));
            System.out.println("\n\nSum of sin^2 and cos^2");

            for (double i = 0; i < 2 * Math.PI; i += 0.1) {
                System.out.printf("i: %f | sin^2 + cos^2: %f \n",
                        i, sumFunc.getFunctionValue(i));
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        Exp expFunc = new Exp();
        TabulatedFunction expTabul;

        try {
            expTabul = TabulatedFunctions.tabulate(expFunc, 0, 10, 11);
            //System.out.println("\n\nTabulated Exp function values:");

            FileWriter outFile = new FileWriter("exp.txt");
            TabulatedFunctions.writeTabulatedFunction(expTabul, outFile);
            outFile.flush();
            outFile.close();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            FileReader in = new FileReader("exp.txt");
            BufferedReader reader = new BufferedReader(in);
            TabulatedFunction expTabulRead = TabulatedFunctions.readTabulatedFunction(reader);
            in.close();
            System.out.println("\n\nBasic Exp function values:");
            for (double i = 0; i < 11; ++i) {
                System.out.printf("x: %f | y: %f\n", i, expFunc.getFunctionValue(i));
            }
            System.out.println("\n\nTabulated Exp read function values:");
            for (double i = 0; i < 11; ++i) {
                System.out.printf("x: %f | y: %f\n", i, expTabulRead.getFunctionValue(i));
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        try {


            Log lnFunc = new Log(Math.E);
            TabulatedFunction lnTabul = TabulatedFunctions.tabulate(lnFunc, 0, 10, 11);

            OutputStream output = new FileOutputStream("log.txt");
            TabulatedFunctions.outputTabulatedFunction(lnTabul, output);
            output.flush();
            output.close();
            //

            InputStream input = new FileInputStream("log.txt");
            TabulatedFunction lnTabulRead = TabulatedFunctions.inputTabulatedFunction(input);
            input.close();

            System.out.println("\n\nBasic ln function values:");
            for (double i = 0; i < 11; ++i) {
                System.out.printf("x: %f | y: %f\n", i, lnFunc.getFunctionValue(i));
            }

            System.out.println("\n\nTabulated ln read function values:");
            for (double i = 0; i < 11; ++i) {
                System.out.printf("x: %f | y: %f\n", i, lnTabulRead.getFunctionValue(i));
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            Log lnFunc2 = new Log(Math.E);
            Exp expFunc2 = new Exp();
            Function f = Functions.composition(lnFunc2, expFunc2);
            TabulatedFunction tF = TabulatedFunctions.tabulate(f, 0., 10., 11);

            System.out.println("\n\nTabulated ln((e)^x) read function values:");
            for (double i = 0; i < 11; ++i) {
                System.out.printf("x: %f | y: %f\n", i, tF.getFunctionValue(i));
            }

            FileOutputStream output2 = new FileOutputStream("ln(exp).txt");
            ObjectOutputStream serial = new ObjectOutputStream(output2);
            serial.writeObject(tF);
            serial.close();
            FileInputStream input2 = new FileInputStream("ln(exp).txt");
            ObjectInputStream deserial = new ObjectInputStream(input2);
            TabulatedFunction tF1 = (TabulatedFunction) deserial.readObject();
            deserial.close();
            System.out.println("ln(e^x) after deserialization");
            System.out.println("\n\nTabulated ln read function values:");
            for (double i = 0; i < 11; ++i) {
                System.out.printf("x: %f | y: %f \n", i, tF1.getFunctionValue(i));
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}