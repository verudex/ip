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
     * @return True if the task was added, false if it's a duplicate.
     */
    public boolean addTask(Task task) {
        assert task != null : "Task to add should not be null";
        if (isDuplicate(task)) {
            return false;
        }
        tasks.add(task);
        return true;
    }

    /**
     * Checks if a task is a duplicate of an existing task.
     *
     * @param task The task to check.
     * @return True if the task is a duplicate, false otherwise.
     */
    public boolean isDuplicate(Task task) {
        assert task != null : "Task should not be null";
        for (Task existingTask : tasks) {
            if (existingTask.equals(task)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds duplicate tasks in the list.
     *
     * @param task The task to find duplicates of.
     * @return A list of indices where duplicates exist.
     */
    public ArrayList<Integer> findDuplicateIndices(Task task) {
        assert task != null : "Task should not be null";
        ArrayList<Integer> duplicateIndices = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).equals(task)) {
                duplicateIndices.add(i);
            }
        }
        return duplicateIndices;
    }

    /**
     * Adds multiple tasks to the list.
     *
     * @param tasksToAdd The tasks to add.
     * @return The number of tasks successfully added (excluding duplicates).
     */
    public int addTasks(Task... tasksToAdd) {
        assert tasksToAdd != null : "Tasks array should not be null";
        int addedCount = 0;
        for (Task task : tasksToAdd) {
            assert task != null : "Individual task should not be null";
            if (addTask(task)) {
                addedCount++;
            }
        }
        return addedCount;
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
