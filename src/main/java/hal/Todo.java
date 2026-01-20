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

    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
