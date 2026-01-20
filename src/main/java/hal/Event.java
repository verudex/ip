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

    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " 
                + from.format(OUTPUT_FORMATTER).toLowerCase() + " to: " 
                + to.format(OUTPUT_FORMATTER).toLowerCase() + ")";
    }
}
