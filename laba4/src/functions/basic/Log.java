package functions.basic;

public class Log extends ContinuosFunctions{
    //LOGa(b)
    private double a;
    public Log(double a){
        this.a = a;
    }
    public double getFunctionValue(double x){
        return x>0? Math.log(x)/Math.log(a) : 0.;
    }
}
