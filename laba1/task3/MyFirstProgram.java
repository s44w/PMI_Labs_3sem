class MyFirstClass{
    public static void main(String[] args){
        MySecondClass o = new MySecondClass();
        int i, j;
        for (i = 1; i <= 8; i++) {
            for(j = 1; j <= 8; j++) {
                o.setA(i);
                o.setB(j);

                System.out.print(o.multiply());
                System.out.print(" ");
            }
            System.out.println();
        }

    }
}

class MySecondClass{
    private int a;
    private int b;


    MySecondClass(){
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