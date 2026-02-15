package hal;

/**
 * Represents a todo task without a specific deadline.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo with the given description.
     *
     * @param description The description of the todo.
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    /**
     * Converts this todo to a format suitable for saving to a file.
     *
     * @return The file format string.
     */
    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }

    /**
     * Returns a string representation of this todo.
     *
     * @return A formatted string showing the todo type.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
