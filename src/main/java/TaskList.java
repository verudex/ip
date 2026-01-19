public class TaskList {
    private String[] tasks;
    private int taskCount;
    private static final int MAX_TASKS = 100;

    public TaskList() {
        this.tasks = new String[MAX_TASKS];
        this.taskCount = 0;
    }

    public void addTask(String task) {
        if (taskCount < MAX_TASKS) {
            tasks[taskCount] = task;
            taskCount++;
        }
    }

    public int getTaskCount() {
        return taskCount;
    }

    public String getTask(int index) {
        if (index >= 0 && index < taskCount) {
            return tasks[index];
        }
        return null;
    }

    public String[] getAllTasks() {
        String[] result = new String[taskCount];
        for (int i = 0; i < taskCount; i++) {
            result[i] = tasks[i];
        }
        return result;
    }
}
