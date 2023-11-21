package functions.meta;

import functions.Function;
import functions.FunctionPointIndexOutOfBoundsException;

public class Mult implements Function {
    private Function func1, func2;

    public Mult(Function func1, Function func2) {
        this.func1 = func1;
        this.func2 = func2;
    }

    public double getLeftDomainBorder() {
        if (Math.abs(func1.getLeftDomainBorder()) < Math.abs(func2.getLeftDomainBorder())){
            return func1.getLeftDomainBorder();
        }
        return func2.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        if (Math.abs(func1.getRightDomainBorder()) < Math.abs(func2.getRightDomainBorder())){
            return func1.getRightDomainBorder();
        }
        return func2.getRightDomainBorder();
    }

    public double getFunctionValue(double x) throws FunctionPointIndexOutOfBoundsException {
        return func1.getFunctionValue(x) * func2.getFunctionValue(x);
    }
}
