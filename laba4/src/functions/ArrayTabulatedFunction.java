package functions;

import java.io.Serializable;

public class ArrayTabulatedFunction implements TabulatedFunction, Function, Serializable {
    private FunctionPoint[] valuesPerPoint;

    private int actualLength;
    private double leftDomainBorder;
    private double rightDomainBorder;
    private FunctionPoint[] points;

    //------------------TASK 3----------------------------------------
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount){
        if ( (leftX>=rightX) || (pointsCount<2) ){
            throw new IllegalArgumentException();
        }
        valuesPerPoint = new FunctionPoint[pointsCount];


        double deltaX = (rightX-leftX)/(pointsCount - 1);
        for (int i =0; i < pointsCount; ++i){
            valuesPerPoint[i] = new FunctionPoint(leftX + i*deltaX, 0);
        }
        actualLength = pointsCount;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        if ( (leftX>=rightX) || (values.length<2) ){
            throw new IllegalArgumentException();
        }
        int len = values.length;
        valuesPerPoint = new FunctionPoint[len];

        double deltaX = (rightDomainBorder-leftDomainBorder)/(len-1);

        for (int i = 0; i < len; ++i) {
            valuesPerPoint[i] = new FunctionPoint(leftX +i*deltaX, values[i]);
        }
        actualLength = len;
    }

    public ArrayTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException {
        if(points.length < 2)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length - 1; i++) {
            if(points[i].getX() > points[i + 1].getX()){
                throw new IllegalArgumentException();
            }
        }
        this.points = points;
        actualLength = points.length;
        //leftDomainBorder = points[0].getX();
        //rightDomainBorder = points[actualLength-1].getX()+points[1].getX();

        valuesPerPoint = new FunctionPoint[actualLength];
        for (int i = 0; i < actualLength; ++i) {
            valuesPerPoint[i] = new FunctionPoint();
            valuesPerPoint[i].setX(points[i].getX());
            valuesPerPoint[i].setY(points[i].getY());
        }
    }
    //---------------------------------------------------------------


    //------------------TASK 4----------------------------------------

    public double getLeftDomainBorder(){
        return points[0].getX();
    }

    public double getRightDomainBorder(){
        return points[actualLength-1].getX() ;//+ points[0].getX();
    }

    private void xValidnessCheck(double x) throws FunctionPointIndexOutOfBoundsException {
        if ((x < this.getLeftDomainBorder()) || (x > this.getRightDomainBorder())) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    private void indexValidnessCheck(double index) throws FunctionPointIndexOutOfBoundsException{
        if ( (index < 0) || (index >= actualLength) ){
            throw new FunctionPointIndexOutOfBoundsException();
        }
    }

    public double getFunctionValue(double x) throws FunctionPointIndexOutOfBoundsException{
            double value = Double.NaN;
            xValidnessCheck(x);
            if (actualLength>1){

                // (y-y1)/(y2-y1) = (x-x1)/(x2-x1)
                int i = 0;
                for (; i < this.points.length; ++i) {
                    if(x == this.points[i].getX())
                        return this.points[i].getY();
                    else if (x < this.points[i].getX()) {
                        break;
                    }
                }

                double y1 = valuesPerPoint[i-1].getY(), y2 = valuesPerPoint[i].getY();
                double x1 = valuesPerPoint[i-1].getX(), x2 = valuesPerPoint[i].getX();

                if ((x2 - x1) != 0 && (y2 - y1) != 0) {
                    value = (y2 - y1) * (x - x1) / (x2 - x1) + y1;
                }
            }
            return value;
    }

    //---------------------------------------------------------------


    //------------------TASK 5----------------------------------------


    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        indexValidnessCheck(index);
        return new FunctionPoint(valuesPerPoint[index]);
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException{
        indexValidnessCheck(index);
        xValidnessCheck(point.getX());
        valuesPerPoint[index] = new FunctionPoint(point);
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException{
        indexValidnessCheck(index);
        xValidnessCheck(x);
        valuesPerPoint[index].setX(x);

    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        indexValidnessCheck(index);
        valuesPerPoint[index].setY(y);
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        indexValidnessCheck(index);
        return valuesPerPoint[index].getX();
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        indexValidnessCheck(index);
        return valuesPerPoint[index].getY();
    }

    public int getActualLength(){
        return actualLength;
    }
    //---------------------------------------------------------------


    //------------------TASK 6----------------------------------------

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException{
        indexValidnessCheck(index);

        int lengthToCut = actualLength - index;
        FunctionPoint[] tmpArray = new FunctionPoint[lengthToCut];
        System.arraycopy(valuesPerPoint, index, valuesPerPoint, index - 1, lengthToCut - 1);
        actualLength-=1;
    }

    int loopSearchIndex(double x){
        for (int i = 0; i < actualLength; ++i){
            if (x>valuesPerPoint[i].getX()){
                return i;
            }
        }
        return actualLength;
    }

    void expandPointsArray(){
        //double newSizeDouble = valuesPerPoint.length*1.5;
        int newSize = (int) (valuesPerPoint.length*1.5);
        FunctionPoint[] newSizedArray = new FunctionPoint[newSize];
        System.arraycopy(valuesPerPoint, 0, newSizedArray, 0, actualLength);

        valuesPerPoint = newSizedArray;
    }

    public void addPoint(FunctionPoint point){
        if (actualLength==valuesPerPoint.length){
            expandPointsArray();
        }

        int indexAdd = loopSearchIndex(point.getX());

        int lengthToCut = actualLength - indexAdd;

        FunctionPoint[] tmpArray = new FunctionPoint[lengthToCut-1];
        System.arraycopy(valuesPerPoint, indexAdd+1, tmpArray, 0, lengthToCut-1);

        valuesPerPoint[indexAdd+1] = new FunctionPoint(point);
        System.arraycopy(tmpArray, 0, valuesPerPoint, indexAdd+2, lengthToCut-1);

        actualLength+=1;

    }

    /*
    int binarySearchIndex(double x){
        int left = 0;
        int right = actualLength;
        int ansIndex = 0;

        while (left<=right){
            int mid = left + ( (right - left)/2);
            if (valuesPerPoint[mid].getX()<x &&
                    valuesPerPoint[mid+1].getX()>x){
                ansIndex = mid+1;
                break;
            }
            if (valuesPerPoint[mid].getX() < x){
                left = mid+1;
            }
            else if (valuesPerPoint[mid].getX()>x) {
                right = mid - 1;
            }
            else{
                ansIndex = mid+1;
                break;
            }
        }
        return ansIndex;
    }
    */

    //---------------------------------------------------------------
}

