package hal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event with a start and end time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, ha");

    /**
     * Constructs an Event with the given description and time range.
     *
     * @param description The description of the event.
     * @param from The start time in "dd/MM/yyyy HHmm" format.
     * @param to The end time in "dd/MM/yyyy HHmm" format.
     */
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = LocalDateTime.parse(from, INPUT_FORMATTER);
        this.to = LocalDateTime.parse(to, INPUT_FORMATTER);
    }

    /**
     * Constructs an Event with the given description and time range.
     *
     * @param description The description of the event.
     * @param from The start time as a LocalDateTime.
     * @param to The end time as a LocalDateTime.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /**
     * Converts this event to a format suitable for saving to a file.
     *
     * @return The file format string.
     */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + from + " | " + to;
    }

    /**
     * Returns a string representation of this event.
     *
     * @return A formatted string showing the event type, start time, and end time.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " 
                + from.format(OUTPUT_FORMATTER).toLowerCase() + " to: " 
                + to.format(OUTPUT_FORMATTER).toLowerCase() + ")";
    }

    /**
     * Checks if this event is equal to another event.
     * Two events are equal if they have the same description, start time, and end time.
     *
     * @param obj The object to compare with.
     * @return True if the events are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Event)) {
            return false;
        }
        Event other = (Event) obj;
        return from.equals(other.from) && to.equals(other.to);
    }

    /**
     * Returns the hash code for this event.
     *
     * @return The hash code based on the description, start time, and end time.
     */
    @Override
    public int hashCode() {
        return super.hashCode() + from.hashCode() + to.hashCode();
    }
}
