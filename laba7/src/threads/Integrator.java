package threads;
import functions.*;

public class Integrator extends Thread {
    Task task;
    Semaphore semaphore;
    boolean flag = true;

    public Integrator(Task t, Semaphore sem) {
        this.task = t;
        this.semaphore = sem;
    }

    public void run() {
        for (int i = 0; i < this.task.getTasksCount() && flag; i++) {
            if(this.task.f == null){
                continue;
            }
            try {
                semaphore.beginRead();
                double res = Functions.integral(this.task.f, this.task.leftX, this.task.rightX, this.task.step);
                System.out.println("   Result <" + this.task.leftX + "> <" + this.task.rightX + "> <" + this.task.step + "> <" + res + ">");
                semaphore.endRead();
            }
            catch(Exception ex){
                System.out.println("Интегратор прервали во время сна, он корректно завершил свою работу");
            }
        }
    }
    public void interrupt() {
        super.interrupt();
        flag = false;
    }
}