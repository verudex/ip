package hal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {
    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        task1 = new Todo("Buy groceries");
        task2 = new Todo("Read book");
        task3 = new Deadline("Submit report", "25/01/2026 2359");
    }

    @Test
    public void addTask_singleTask_success() {
        taskList.addTask(task1);
        assertEquals(1, taskList.getTaskCount());
        assertEquals(task1, taskList.getTask(0));
    }

    @Test
    public void addTask_multipleTasks_success() {
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);
        assertEquals(3, taskList.getTaskCount());
        assertEquals(task1, taskList.getTask(0));
        assertEquals(task2, taskList.getTask(1));
        assertEquals(task3, taskList.getTask(2));
    }

    @Test
    public void deleteTask_validIndex_success() {
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);
        
        Task deleted = taskList.deleteTask(1);
        assertEquals(task2, deleted);
        assertEquals(2, taskList.getTaskCount());
        assertEquals(task1, taskList.getTask(0));
        assertEquals(task3, taskList.getTask(1));
    }

    @Test
    public void deleteTask_invalidIndex_returnsNull() {
        taskList.addTask(task1);
        assertNull(taskList.deleteTask(5));
        assertNull(taskList.deleteTask(-1));
        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    public void deleteTask_emptyList_returnsNull() {
        assertNull(taskList.deleteTask(0));
        assertEquals(0, taskList.getTaskCount());
    }

    @Test
    public void markTask_validIndex_success() {
        taskList.addTask(task1);
        assertFalse(task1.isDone());
        taskList.markTask(0);
        assertTrue(task1.isDone());
    }

    @Test
    public void markTask_invalidIndex_noEffect() {
        taskList.addTask(task1);
        taskList.markTask(5);
        taskList.markTask(-1);
        assertFalse(task1.isDone());
    }

    @Test
    public void unmarkTask_validIndex_success() {
        taskList.addTask(task1);
        task1.markAsDone();
        assertTrue(task1.isDone());
        taskList.unmarkTask(0);
        assertFalse(task1.isDone());
    }

    @Test
    public void unmarkTask_invalidIndex_noEffect() {
        taskList.addTask(task1);
        task1.markAsDone();
        taskList.unmarkTask(5);
        taskList.unmarkTask(-1);
        assertTrue(task1.isDone());
    }

    @Test
    public void getTask_validIndex_returnsTask() {
        taskList.addTask(task1);
        taskList.addTask(task2);
        assertEquals(task1, taskList.getTask(0));
        assertEquals(task2, taskList.getTask(1));
    }

    @Test
    public void getTask_invalidIndex_returnsNull() {
        taskList.addTask(task1);
        assertNull(taskList.getTask(5));
        assertNull(taskList.getTask(-1));
    }

    @Test
    public void getTaskCount_emptyList_returnsZero() {
        assertEquals(0, taskList.getTaskCount());
    }

    @Test
    public void getTaskCount_afterAddingTasks_returnsCorrectCount() {
        taskList.addTask(task1);
        assertEquals(1, taskList.getTaskCount());
        taskList.addTask(task2);
        assertEquals(2, taskList.getTaskCount());
        taskList.addTask(task3);
        assertEquals(3, taskList.getTaskCount());
    }

    @Test
    public void getTaskCount_afterDeletingTasks_returnsCorrectCount() {
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);
        assertEquals(3, taskList.getTaskCount());
        
        taskList.deleteTask(1);
        assertEquals(2, taskList.getTaskCount());
        
        taskList.deleteTask(0);
        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    public void getAllTasks_returnsAllTasks() {
        taskList.addTask(task1);
        taskList.addTask(task2);
        assertEquals(2, taskList.getAllTasks().size());
        assertTrue(taskList.getAllTasks().contains(task1));
        assertTrue(taskList.getAllTasks().contains(task2));
    }
}
