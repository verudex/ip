package hal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("Sample task");
    }

    @Test
    public void constructor_createsTaskWithDescription() {
        assertEquals("Sample task", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    public void markAsDone_unmarkedTask_marksTaskAsDone() {
        assertFalse(task.isDone());
        task.markAsDone();
        assertTrue(task.isDone());
    }

    @Test
    public void markAsDone_alreadyMarkedTask_remainsMarked() {
        task.markAsDone();
        assertTrue(task.isDone());
        task.markAsDone();
        assertTrue(task.isDone());
    }

    @Test
    public void markAsNotDone_markedTask_unmarksTask() {
        task.markAsDone();
        assertTrue(task.isDone());
        task.markAsNotDone();
        assertFalse(task.isDone());
    }

    @Test
    public void markAsNotDone_unmarkedTask_remainsUnmarked() {
        assertFalse(task.isDone());
        task.markAsNotDone();
        assertFalse(task.isDone());
    }

    @Test
    public void getStatusIcon_unmarkedTask_returnsSpace() {
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void getStatusIcon_markedTask_returnsX() {
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void toFileFormat_unmarkedTask_correctFormat() {
        assertEquals("0 | Sample task", task.toFileFormat());
    }

    @Test
    public void toFileFormat_markedTask_correctFormat() {
        task.markAsDone();
        assertEquals("1 | Sample task", task.toFileFormat());
    }

    @Test
    public void toString_unmarkedTask_correctFormat() {
        assertEquals("[ ] Sample task", task.toString());
    }

    @Test
    public void toString_markedTask_correctFormat() {
        task.markAsDone();
        assertEquals("[X] Sample task", task.toString());
    }

    @Test
    public void isDone_newTask_returnsFalse() {
        assertFalse(task.isDone());
    }

    @Test
    public void isDone_afterMarking_returnsTrue() {
        task.markAsDone();
        assertTrue(task.isDone());
    }

    @Test
    public void isDone_afterUnmarking_returnsFalse() {
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone());
    }
}
