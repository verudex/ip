package hal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, ha");

    /**
     * Constructs a Deadline with the given description and deadline string.
     *
     * @param description The description of the deadline task.
     * @param by The deadline in "dd/MM/yyyy HHmm" format.
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = LocalDateTime.parse(by, INPUT_FORMATTER);
    }

    /**
     * Constructs a Deadline with the given description and deadline.
     *
     * @param description The description of the deadline task.
     * @param by The deadline as a LocalDateTime.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat() + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " 
                + by.format(OUTPUT_FORMATTER).toLowerCase() + ")";
    }

    /**
     * Checks if this deadline is equal to another deadline.
     * Two deadlines are equal if they have the same description and deadline time.
     *
     * @param obj The object to compare with.
     * @return True if the deadlines are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Deadline)) {
            return false;
        }
        Deadline other = (Deadline) obj;
        return by.equals(other.by);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + by.hashCode();
    }
}
