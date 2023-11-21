package functions;

import java.awt.*;

public class TabulatedFunction {
    private FunctionPoint[] valuesPerPoint;
    private int actualLength;
    private double leftDomainBorder;
    private double rightDomainBorder;

    //------------------TASK 3----------------------------------------
    public TabulatedFunction(double leftX, double rightX, int pointsCount){
        leftDomainBorder = leftX;
        rightDomainBorder = rightX;
        valuesPerPoint = new FunctionPoint[pointsCount];


        //double deltaX = (rightX-leftX)/(pointsCount-1);
        for (int i =0; i < pointsCount; ++i){
            valuesPerPoint[i] = new FunctionPoint(leftX + i, 0);
        }
        actualLength = pointsCount;
    }

    public TabulatedFunction(double leftX, double rightX, double[] values){
        leftDomainBorder = leftX;
        rightDomainBorder = rightX;

        int len = values.length;
        valuesPerPoint = new FunctionPoint[len];

        //double deltaX = (values[len-1] - values[0])/(len-1);

        for (int i = 0; i < len; ++i) {
            valuesPerPoint[i] = new FunctionPoint(leftX +i, values[i]);
        }
        actualLength = len;
    }
    //---------------------------------------------------------------


    //------------------TASK 4----------------------------------------

    public double getLeftDomainBorder(){
        return leftDomainBorder;
    }

    public double getRightDomainBorder(){
        return rightDomainBorder;
    }

    public double getFunctionValue(double x){
        double value = Double.NaN;
        if ( (leftDomainBorder < x) && (x < rightDomainBorder)
                && actualLength>1) {

            double y1 = valuesPerPoint[0].getY(), y2 = valuesPerPoint[1].getY();
            double x1 = valuesPerPoint[0].getX(), x2 = valuesPerPoint[1].getX();
            // (y-y1)/(y2-y1) = (x-x1)/(x2-x1)

            if ((x2 - x1) != 0 && (y2 - y1) != 0) {
                value = (y2 - y1) * (x - x1) / (x2 - x1) + y1;
            }
        }
        return value;
    }

    //---------------------------------------------------------------


    //------------------TASK 5----------------------------------------

    public int getPointsCount(){
        return actualLength;
    }

    public FunctionPoint getPoint(int index){
        return valuesPerPoint[index];
    }

    public void setPoint(int index, FunctionPoint point){
        if (leftDomainBorder < point.getX() && point.getX() < rightDomainBorder){
            valuesPerPoint[index] = new FunctionPoint(point);
        }
    }

    public void setPointX(int index, double x) {
        if (leftDomainBorder < x && x < rightDomainBorder) {
            valuesPerPoint[index].setX(x);
        }
    }

    public void setPointY(int index, double y) {
        valuesPerPoint[index].setY(y);
    }

    public double getPointX(int index){
        return valuesPerPoint[index].getX();
    }

    public double getPointY(int index){
        return valuesPerPoint[index].getY();
    }

    public int getActualLength(){
        return actualLength;
    }
    //---------------------------------------------------------------


    //------------------TASK 6----------------------------------------

    public void deletePoint(int index){
        if (index >= 0 && index < actualLength) {
            int lengthToCut = actualLength - index;
            FunctionPoint[] tmpArray = new FunctionPoint[lengthToCut];
            System.arraycopy(valuesPerPoint, index, valuesPerPoint, index - 1, lengthToCut - 1);
            actualLength-=1;
        }
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
