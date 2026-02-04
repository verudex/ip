package hal;

import java.util.ArrayList;

/**
 * Manages a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        assert task != null : "Task to add should not be null";
        tasks.add(task);
    }

    /**
     * Adds multiple tasks to the list.
     *
     * @param tasksToAdd The tasks to add.
     */
    public void addTasks(Task... tasksToAdd) {
        assert tasksToAdd != null : "Tasks array should not be null";
        for (Task task : tasksToAdd) {
            assert task != null : "Individual task should not be null";
            tasks.add(task);
        }
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The task count.
     */
    public int getTaskCount() {
        int count = tasks.size();
        assert count >= 0 : "Task count should never be negative";
        return count;
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The index of the task.
     * @return The task at the index, or null if invalid.
     */
    public Task getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            assert task != null : "Task in list should not be null";
            return task;
        }
        return null;
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index The index of the task to mark.
     */
    public void markTask(int index) {
        assert index >= 0 : "Index should not be negative";
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            assert task != null : "Task should not be null";
            task.markAsDone();
            assert task.isDone() : "Task should be marked as done";
        }
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index The index of the task to unmark.
     */
    public void unmarkTask(int index) {
        assert index >= 0 : "Index should not be negative";
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            assert task != null : "Task should not be null";
            task.markAsNotDone();
            assert !task.isDone() : "Task should be marked as not done";
        }
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index The index of the task to delete.
     * @return The deleted task, or null if invalid.
     */
    public Task deleteTask(int index) {
        assert index >= 0 : "Index should not be negative";
        if (index >= 0 && index < tasks.size()) {
            Task deletedTask = tasks.remove(index);
            assert deletedTask != null : "Deleted task should not be null";
            return deletedTask;
        }
        return null;
    }

    /**
     * Returns all tasks in the list.
     *
     * @return The list of all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        assert tasks != null : "Task list should not be null";
        return tasks;
    }

    /**
     * Finds tasks that contain the keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return A list of tasks containing the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        assert keyword != null : "Keyword should not be null";
        ArrayList<Task> foundTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            assert task != null : "Task in list should not be null";
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                foundTasks.add(task);
            }
        }
        assert foundTasks != null : "Found tasks list should not be null";
        return foundTasks;
    }
}
