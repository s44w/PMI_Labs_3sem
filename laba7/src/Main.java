import functions.*;
import functions.basic.*;
import threads.*;
import java.io.*;
import java.util.Random;


public class Main {
    public static void nonThread() {
        Task task = new Task(100);
        Random rand = new Random();

        try {
            for (int i = 0; i < task.getTasksCount(); ++i) {
                Log log = new Log(rand.nextInt(9) + 1);
                int leftX = rand.nextInt(100);
                int rightX = rand.nextInt(100) + 100;
                double step = (double) 1 / (rand.nextDouble(100000.) + 1.0);
                System.out.println(i + ": Source <" + leftX + "> <" + rightX + "> <" + step + ">");
                System.out.println("    Result <" + leftX + "> <" + rightX + "> <" + step + "> <" + Functions.integral(log, leftX, rightX, step) + ">");
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void simpleThreads() throws InterruptedException{
        Task t = new Task(100);
        Thread generator_thread = new Thread(new SimpleGenerator(t));
        generator_thread.start();
        Thread integrator_thread = new Thread(new SimpleIntegrator(t));
        integrator_thread.start();
        Thread.sleep(50);
    }

    public static void complicatedThreads() throws InterruptedException {
        Task t = new Task(100);
        Semaphore semaphore = new Semaphore();
        Thread generator = new Generator(t, semaphore);
        generator.start();

        Thread integrator = new Integrator(t, semaphore);
        System.out.println("Generator priority = " + generator.getPriority());
        System.out.println("Integrator priority = " + integrator.getPriority());
        integrator.start();
        Thread.sleep(50);
        generator.interrupt();
        integrator.interrupt();
    }

    public static void Task1output(){
        /*Task 1:
        Вычислите интеграл для экспоненты на отрезке от 0 до 1.
        Определите также, какой шаг дискретизации нужен,
        чтобы рассчитанное значение отличалось от теоретического
        в 7 знаке после запятой.
        */
        System.out.println("TASK 1");
        Exp exp = new Exp();
        // 1,7182818284590452353602874713527 интеграл от e^x от 0 до 1
        try {
            System.out.println(Functions.integral(exp, 0, 1, 0.00000001));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void Task2output(){
        /*
        1)	создать и поместить в объект задания объект логарифмической функции,
        основание которой является случайной величиной, распределённой равномерно на отрезке от 1 до 10;
        2)	указать в объекте задания левую границу области интегрирования
        (случайно распределена на отрезке от 0 до 100);
        3)	указать в объекте задания правую границу области интегрирования
        (случайно распределена на отрезке от 100 до 200);
        4)	указать в объекте задания шаг дискретизации
        (случайно распределён на отрезке от 0 до 1);
        5)	вывести в консоль сообщение вида
        "Source <левая граница> <правая граница> <шаг дискретизации>";
        6)	вычислить значение интеграла для параметров из объекта задания;
        7)	вывести в консоль сообщение вида
        "Result <левая граница> <правая граница> <шаг дискретизации> <результат интегрирования>".
         */
        System.out.println("\n\n TASK 2");
        nonThread();
    }

    public static void Task3output(){
        System.out.println("\n\n TASK 3");
        try{
            simpleThreads();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void Task4output(){
        /*
        В главном классе программы создайте метод complicatedThreads().
        В нём создайте объект задания, укажите количество выполняемых заданий
        (минимум 100), создайте и запустите два потока вычислений классов
         Generator и Integrator. Проверьте работу метода, вызвав его в методе main().
         */
        System.out.println("\n\n TASK 4");
        try {
            complicatedThreads();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args){
        Task1output();
        Task2output();
        Task3output();
        //wait(30);
        Task4output();
    }
    /*
    public static void main(String[] args) {
        double[] arr = {1., 2., 3., 4., 5., 6., 7., 8., 9., 10.};
        double[] arr2 = {1, 2., 3., 4., 5., 6., 7., 8., 9.01, 10.};

        try {
            ArrayTabulatedFunction arrayFunc1 = new ArrayTabulatedFunction(1, 10, arr);
            ArrayTabulatedFunction arrayFunc2 = new ArrayTabulatedFunction(1, 10, arr2);
            LinkedListTabulatedFunction listFunc1 = new LinkedListTabulatedFunction(1, 10, arr);
            LinkedListTabulatedFunction listFunc2 = new LinkedListTabulatedFunction(1, 9, arr);
            ArrayTabulatedFunction arrayCloned1 = (ArrayTabulatedFunction) arrayFunc1.clone();

            System.out.println("ArrayTabulatedFunction:\n" + arrayFunc1.toString());
            System.out.println("Second ArrayTabulatedFunction:\n" + arrayFunc2.toString());

            System.out.println("LinkedListTabulatedFunction:\n" + listFunc1.toString());
            System.out.println("Second LinkedListTabulatedFunction:\n" + listFunc2.toString());


            System.out.println("\n\nDoes ArrayTabulatedFunction equal LinkedListTabulatedFunction?\n" + arrayFunc1.equals(listFunc1));
            System.out.println("Does LinkedListTabulatedFunction equal ArrayTabulatedFunction?\n" + listFunc1.equals(arrayFunc1));


            System.out.println("Does ArrayTabulatedFunction equal Second LinkedListTabulatedFunction?\n" + arrayFunc1.equals(listFunc2));
            System.out.println("Does Second LinkedListTabulatedFunction equal ArrayTabulatedFunction?\n" + listFunc2.equals(arrayFunc1));

            System.out.println("\n\nArray`s hashCode: " + arrayFunc1.hashCode());
            System.out.println("LinkedList`s hashCode: " + listFunc1.hashCode());
            System.out.println("Second LinkedList`s hashCode: " + listFunc2.hashCode());
            System.out.println("Changed Array`s x9, function`s hashcode:  " + arrayFunc2.hashCode());

            System.out.println("\n\nCloned Array function after deleting Array`s point: ");
            arrayFunc1.deletePoint(2);
            System.out.println("ArrayTabulatedFunction:\n" + arrayFunc1.toString());
            System.out.println("Cloned ArrayTabulatedFunction:\n" + arrayCloned1.toString());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

     */
}