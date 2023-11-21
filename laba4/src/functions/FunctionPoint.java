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
}
