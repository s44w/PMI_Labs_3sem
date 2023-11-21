package threads;
import functions.*;

public class Task {
    public Function f;
    public double leftX, rightX;
    public double step;
    private int tasksCount;

    public Task(int tasks) {
        this.tasksCount = tasks;
    }

    public Task(Function f, double leftX, double rightX, double step, int tasksCount) {
        this.f = f;
        this.leftX = leftX;
        this.rightX = rightX;
        this.step = step;
        this.tasksCount = tasksCount;
    }

    public int getTasksCount() {
        return this.tasksCount;
    }
}