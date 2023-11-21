package myfirstpackage;
public class MySecondClass{
    private int a;
    private int b;


    public MySecondClass(){
        a = 0;
        b = 0;
    }

    public void setA(int val){
        a = val;
    }

    public void setB(int val){
        b = val;
    }

    public int getA(){
        return a;
    }
    public int getB(){
        return b;
    }

    public int multiply(){
        return a*b;
    }
}