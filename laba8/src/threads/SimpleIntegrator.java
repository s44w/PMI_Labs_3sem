package threads;

import functions.FunctionPointIndexOutOfBoundsException;
import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private final Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    public void run() {
        synchronized (task) {
            try {
                for (int i = 0; i < this.task.getTasksCount(); i++) {
                    if (this.task.f != null) {
                        double res = Functions.integral(this.task.f, this.task.leftX, this.task.rightX, this.task.step);
                        System.out.println(i + ": Result <" + this.task.leftX + "> <" + this.task.rightX + "> <" + this.task.step + "> <" + res + ">");
                    }

                }

            } catch (Exception ex) {

            }
        }
    }
}