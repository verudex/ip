package hal;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType taskType;

    /**
     * Constructs a Task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description should not be null";
        assert !description.trim().isEmpty() : "Task description should not be empty";
        this.description = description;
        this.isDone = false;
        this.taskType = null;
    }

    /**
     * Constructs a Task with the given description and task type.
     *
     * @param description The description of the task.
     * @param taskType The type of the task.
     */
    public Task(String description, TaskType taskType) {
        assert description != null : "Task description should not be null";
        assert !description.trim().isEmpty() : "Task description should not be empty";
        assert taskType != null : "Task type should not be null";
        this.description = description;
        this.isDone = false;
        this.taskType = taskType;
    }

    /**
     * Returns the type of this task.
     *
     * @return The task type.
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * Returns the status icon representing the completion status.
     *
     * @return "X" if done, " " if not done.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return The task description.
     */
    public String getDescription() {
        assert description != null : "Description should not be null";
        return description;
    }

    /**
     * Returns whether this task is done.
     *
     * @return True if done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Converts this task to a format suitable for saving to a file.
     *
     * @return The file format string.
     */
    public String toFileFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of this task.
     *
     * @return A formatted string showing the status icon and description.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Checks if this task is equal to another task.
     * Two tasks are considered equal if they have the same description and type.
     *
     * @param obj The object to compare with.
     * @return True if the tasks are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        return description.equalsIgnoreCase(other.description);
    }

    /**
     * Returns the hash code for this task.
     *
     * @return The hash code based on the description.
     */
    @Override
    public int hashCode() {
        return description.toLowerCase().hashCode();
    }
}
