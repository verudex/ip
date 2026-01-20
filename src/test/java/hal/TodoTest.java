package hal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {

    @Test
    public void constructor_validDescription_createsTodo() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
        assertFalse(todo.isDone());
        assertEquals(TaskType.TODO, todo.getTaskType());
    }

    @Test
    public void toFileFormat_unmarkedTodo_correctFormat() {
        Todo todo = new Todo("Read book");
        assertEquals("T | 0 | Read book", todo.toFileFormat());
    }

    @Test
    public void toFileFormat_markedTodo_correctFormat() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        assertEquals("T | 1 | Read book", todo.toFileFormat());
    }

    @Test
    public void toString_unmarkedTodo_correctFormat() {
        Todo todo = new Todo("Exercise");
        assertEquals("[T][ ] Exercise", todo.toString());
    }

    @Test
    public void toString_markedTodo_correctFormat() {
        Todo todo = new Todo("Exercise");
        todo.markAsDone();
        assertEquals("[T][X] Exercise", todo.toString());
    }

    @Test
    public void markAsDone_todo_changesStatus() {
        Todo todo = new Todo("Clean room");
        assertFalse(todo.isDone());
        todo.markAsDone();
        assertTrue(todo.isDone());
    }

    @Test
    public void markAsNotDone_todo_changesStatus() {
        Todo todo = new Todo("Clean room");
        todo.markAsDone();
        assertTrue(todo.isDone());
        todo.markAsNotDone();
        assertFalse(todo.isDone());
    }

    @Test
    public void getTaskType_todo_returnsTodoType() {
        Todo todo = new Todo("Water plants");
        assertEquals(TaskType.TODO, todo.getTaskType());
    }
}
