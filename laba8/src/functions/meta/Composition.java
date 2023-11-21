package functions.meta;

import functions.Function;
import functions.FunctionPointIndexOutOfBoundsException;
public class Composition implements Function {
    private Function func1, func2;

    public Composition(Function func1, Function func2) {
        this.func1 = func1;
        this.func2 = func2;
    }

    public double getLeftDomainBorder() {
        return func1.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return func1.getRightDomainBorder();
    }

    public double getFunctionValue(double x) throws FunctionPointIndexOutOfBoundsException {
        return func1.getFunctionValue(func2.getFunctionValue(x));
    }
}