public class TaskList {
    private Task[] tasks;
    private int taskCount;
    private static final int MAX_TASKS = 100;

    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.taskCount = 0;
    }

    public void addTask(Task task) {
        if (taskCount < MAX_TASKS) {
            tasks[taskCount] = task;
            taskCount++;
        }
    }

    public int getTaskCount() {
        return taskCount;
    }

    public Task getTask(int index) {
        if (index >= 0 && index < taskCount) {
            return tasks[index];
        }
        return null;
    }

    public void markTask(int index) {
        if (index >= 0 && index < taskCount) {
            tasks[index].markAsDone();
        }
    }

    public void unmarkTask(int index) {
        if (index >= 0 && index < taskCount) {
            tasks[index].markAsNotDone();
        }
    }
}
