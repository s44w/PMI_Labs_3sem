package functions.meta;

import functions.Function;
import functions.FunctionPointIndexOutOfBoundsException;

public class Scale implements Function {
    private Function func1;
    private double scaleX, scaleY;

    public Scale(Function func1, double scaleX, double scaleY) {
        this.func1 = func1;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public double getLeftDomainBorder() {
        return Math.abs(func1.getLeftDomainBorder())*scaleX;
    }

    public double getRightDomainBorder() {
        return Math.abs(func1.getRightDomainBorder())*scaleX;
    }

    public double getFunctionValue(double x) throws FunctionPointIndexOutOfBoundsException {
        return func1.getFunctionValue(x) * scaleY;
    }
}