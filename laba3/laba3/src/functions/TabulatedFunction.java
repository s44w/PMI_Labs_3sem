package functions;

public interface TabulatedFunction {
    public double getLeftDomainBorder();

    public double getRightDomainBorder();

    public int getActualLength();

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException;

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException;

    public void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException;

    public double getPointY(int index) throws IllegalAccessError, FunctionPointIndexOutOfBoundsException;

    public void setPointY(int index, double y) throws IllegalAccessError, FunctionPointIndexOutOfBoundsException;

    public double getFunctionValue(double x) throws FunctionPointIndexOutOfBoundsException;

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException;

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException;
}

