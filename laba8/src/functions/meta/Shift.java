package functions.meta;

import functions.Function;
import functions.FunctionPointIndexOutOfBoundsException;

public class Shift implements Function {
    private Function func1;
    private double shiftX, shiftY;

    public Shift (Function func1, double shiftX, double shiftY) {
        this.func1 = func1;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    public double getLeftDomainBorder() {
        return Math.abs(func1.getLeftDomainBorder()) + shiftX;
    }

    public double getRightDomainBorder() {
        return Math.abs(func1.getRightDomainBorder()) + shiftX;
    }

    public double getFunctionValue(double x) throws FunctionPointIndexOutOfBoundsException {
        return func1.getFunctionValue(x + shiftX) + shiftY;
    }
}