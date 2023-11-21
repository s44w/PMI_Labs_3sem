package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
    private double x;
    private double y;

    FunctionPoint(){
        setX(0);
        setY(0);
    }


    public FunctionPoint(double x, double y){
        setX(x);
        setY(y);
    }

    public FunctionPoint(FunctionPoint point){
        double tmpX = point.getX();
        setX(tmpX);

        double tmpY = point.getY();
        setY(tmpY);
    }

    void setX(double x){
        this.x = x;
    }
    void setY(double y){
        this.y = y;
    }

    double getX(){
        return x;
    }
    double getY(){
        return y;
    }


    public String toString(){
        return "(" + x + "; " + y + ")";
    }

    public boolean equals(Object obj){
        boolean equal = false;
        if(obj instanceof FunctionPoint point){
            equal = (x == point.x && y == point.y);
        }
        return equal;
    }

    public int hashCode(){
        final int prime = 23;
        int res = 1;
        res =  (int) (prime * (res + this.getX()));
        res =  (int) (prime * (res + this.getY()));
        return res;
        //return (int)(Double.doubleToLongBits(x) ^ Double.doubleToLongBits(y));
    }

    public Object clone(){
        return new FunctionPoint(this);
    }
}
