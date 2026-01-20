package hal;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventTest {

    @Test
    public void constructor_validDateStrings_createsEvent() {
        Event event = new Event("Project meeting", "25/01/2026 1400", "25/01/2026 1600");
        assertEquals("Project meeting", event.getDescription());
        assertFalse(event.isDone());
        assertEquals(TaskType.EVENT, event.getTaskType());
    }

    @Test
    public void constructor_validLocalDateTimes_createsEvent() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 25, 14, 0);
        LocalDateTime to = LocalDateTime.of(2026, 1, 25, 16, 0);
        Event event = new Event("Project meeting", from, to);
        assertEquals("Project meeting", event.getDescription());
        assertFalse(event.isDone());
    }

    @Test
    public void constructor_invalidFromDateFormat_throwsException() {
        assertThrows(Exception.class, () -> {
            new Event("Meeting", "25-01-2026", "25/01/2026 1600");
        });
    }

    @Test
    public void constructor_invalidToDateFormat_throwsException() {
        assertThrows(Exception.class, () -> {
            new Event("Meeting", "25/01/2026 1400", "25-01-2026");
        });
    }

    @Test
    public void constructor_invalidDates_throwsException() {
        assertThrows(Exception.class, () -> {
            new Event("Meeting", "32/13/2026 2500", "25/01/2026 1600");
        });
    }

    @Test
    public void toFileFormat_unmarkedEvent_correctFormat() {
        Event event = new Event("Conference", "25/01/2026 0900", "25/01/2026 1700");
        String fileFormat = event.toFileFormat();
        assertTrue(fileFormat.startsWith("E | 0 | Conference | "));
        assertTrue(fileFormat.contains("2026-01-25T09:00"));
        assertTrue(fileFormat.contains("2026-01-25T17:00"));
    }

    @Test
    public void toFileFormat_markedEvent_correctFormat() {
        Event event = new Event("Conference", "25/01/2026 0900", "25/01/2026 1700");
        event.markAsDone();
        String fileFormat = event.toFileFormat();
        assertTrue(fileFormat.startsWith("E | 1 | Conference | "));
        assertTrue(fileFormat.contains("2026-01-25T09:00"));
        assertTrue(fileFormat.contains("2026-01-25T17:00"));
    }

    @Test
    public void toString_unmarkedEvent_correctFormat() {
        Event event = new Event("Team meeting", "25/01/2026 1400", "25/01/2026 1600");
        String output = event.toString();
        assertEquals("[E][ ] Team meeting (from: jan 25 2026, 2pm to: jan 25 2026, 4pm)", output);
    }

    @Test
    public void toString_markedEvent_correctFormat() {
        Event event = new Event("Team meeting", "25/01/2026 1400", "25/01/2026 1600");
        event.markAsDone();
        String output = event.toString();
        assertEquals("[E][X] Team meeting (from: jan 25 2026, 2pm to: jan 25 2026, 4pm)", output);
    }

    @Test
    public void toString_morningEvent_correctFormatting() {
        Event event = new Event("Breakfast meeting", "20/02/2026 0800", "20/02/2026 0900");
        String output = event.toString();
        assertEquals("[E][ ] Breakfast meeting (from: feb 20 2026, 8am to: feb 20 2026, 9am)", output);
    }

    @Test
    public void toString_eveningEvent_correctFormatting() {
        Event event = new Event("Dinner party", "15/03/2026 1900", "15/03/2026 2200");
        String output = event.toString();
        assertEquals("[E][ ] Dinner party (from: mar 15 2026, 7pm to: mar 15 2026, 10pm)", output);
    }

    @Test
    public void toString_multiDayEvent_correctFormatting() {
        Event event = new Event("Conference", "20/04/2026 0900", "22/04/2026 1700");
        String output = event.toString();
        assertEquals("[E][ ] Conference (from: apr 20 2026, 9am to: apr 22 2026, 5pm)", output);
    }

    @Test
    public void markAsDone_event_changesStatus() {
        Event event = new Event("Workshop", "25/01/2026 1000", "25/01/2026 1200");
        assertFalse(event.isDone());
        event.markAsDone();
        assertTrue(event.isDone());
    }
}
