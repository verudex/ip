package hal;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineTest {

    @Test
    public void constructor_validDateString_createsDeadline() {
        Deadline deadline = new Deadline("Submit assignment", "25/01/2026 2359");
        assertEquals("Submit assignment", deadline.getDescription());
        assertFalse(deadline.isDone());
        assertEquals(TaskType.DEADLINE, deadline.getTaskType());
    }

    @Test
    public void constructor_validLocalDateTime_createsDeadline() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 1, 25, 23, 59);
        Deadline deadline = new Deadline("Submit assignment", dateTime);
        assertEquals("Submit assignment", deadline.getDescription());
        assertFalse(deadline.isDone());
    }

    @Test
    public void constructor_invalidDateFormat_throwsException() {
        assertThrows(Exception.class, () -> {
            new Deadline("Submit assignment", "25-01-2026");
        });
    }

    @Test
    public void constructor_invalidDate_throwsException() {
        assertThrows(Exception.class, () -> {
            new Deadline("Submit assignment", "32/13/2026 2500");
        });
    }

    @Test
    public void toFileFormat_unmarkedDeadline_correctFormat() {
        Deadline deadline = new Deadline("Submit assignment", "25/01/2026 2359");
        String fileFormat = deadline.toFileFormat();
        assertTrue(fileFormat.startsWith("D | 0 | Submit assignment | "));
        assertTrue(fileFormat.contains("2026-01-25T23:59"));
    }

    @Test
    public void toFileFormat_markedDeadline_correctFormat() {
        Deadline deadline = new Deadline("Submit assignment", "25/01/2026 2359");
        deadline.markAsDone();
        String fileFormat = deadline.toFileFormat();
        assertTrue(fileFormat.startsWith("D | 1 | Submit assignment | "));
        assertTrue(fileFormat.contains("2026-01-25T23:59"));
    }

    @Test
    public void toString_unmarkedDeadline_correctFormat() {
        Deadline deadline = new Deadline("Submit assignment", "25/01/2026 2359");
        String output = deadline.toString();
        assertEquals("[D][ ] Submit assignment (by: jan 25 2026, 11pm)", output);
    }

    @Test
    public void toString_markedDeadline_correctFormat() {
        Deadline deadline = new Deadline("Submit assignment", "25/01/2026 2359");
        deadline.markAsDone();
        String output = deadline.toString();
        assertEquals("[D][X] Submit assignment (by: jan 25 2026, 11pm)", output);
    }

    @Test
    public void toString_differentTime_correctFormatting() {
        Deadline deadline = new Deadline("Meeting", "15/03/2026 1400");
        String output = deadline.toString();
        assertEquals("[D][ ] Meeting (by: mar 15 2026, 2pm)", output);
    }

    @Test
    public void toString_morningTime_correctFormatting() {
        Deadline deadline = new Deadline("Breakfast", "20/02/2026 0900");
        String output = deadline.toString();
        assertEquals("[D][ ] Breakfast (by: feb 20 2026, 9am)", output);
    }

    @Test
    public void markAsDone_deadline_changesStatus() {
        Deadline deadline = new Deadline("Submit assignment", "25/01/2026 2359");
        assertFalse(deadline.isDone());
        deadline.markAsDone();
        assertTrue(deadline.isDone());
    }
}
