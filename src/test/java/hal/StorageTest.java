package hal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {
    @TempDir
    Path tempDir;

    private Storage storage;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        testFilePath = tempDir.resolve("test_hal.txt").toString();
        storage = new Storage(testFilePath);
    }

    @Test
    public void load_emptyFile_returnsEmptyList() {
        ArrayList<Task> tasks = storage.load();
        assertEquals(0, tasks.size());
    }

    @Test
    public void save_singleTodo_savesCorrectly() {
        ArrayList<Task> tasks = new ArrayList<>();
        Task todo = new Todo("Buy milk");
        tasks.add(todo);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(1, loadedTasks.size());
        assertEquals("Buy milk", loadedTasks.get(0).getDescription());
        assertFalse(loadedTasks.get(0).isDone());
    }

    @Test
    public void save_markedTodo_savesAndLoadsCorrectly() {
        ArrayList<Task> tasks = new ArrayList<>();
        Task todo = new Todo("Buy milk");
        todo.markAsDone();
        tasks.add(todo);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0).isDone());
    }

    @Test
    public void save_deadline_savesAndLoadsCorrectly() {
        ArrayList<Task> tasks = new ArrayList<>();
        Task deadline = new Deadline("Submit report", "25/01/2026 2359");
        tasks.add(deadline);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(1, loadedTasks.size());
        assertEquals("Submit report", loadedTasks.get(0).getDescription());
        assertEquals(TaskType.DEADLINE, loadedTasks.get(0).getTaskType());
        assertFalse(loadedTasks.get(0).isDone());
    }

    @Test
    public void save_event_savesAndLoadsCorrectly() {
        ArrayList<Task> tasks = new ArrayList<>();
        Task event = new Event("Conference", "25/01/2026 0900", "25/01/2026 1700");
        tasks.add(event);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(1, loadedTasks.size());
        assertEquals("Conference", loadedTasks.get(0).getDescription());
        assertEquals(TaskType.EVENT, loadedTasks.get(0).getTaskType());
        assertFalse(loadedTasks.get(0).isDone());
    }

    @Test
    public void save_multipleTasks_savesAndLoadsCorrectly() {
        ArrayList<Task> tasks = new ArrayList<>();
        Task todo = new Todo("Buy groceries");
        Task deadline = new Deadline("Submit assignment", "25/01/2026 2359");
        Task event = new Event("Team meeting", "26/01/2026 1400", "26/01/2026 1600");
        
        todo.markAsDone();
        tasks.add(todo);
        tasks.add(deadline);
        tasks.add(event);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(3, loadedTasks.size());
        assertTrue(loadedTasks.get(0).isDone());
        assertFalse(loadedTasks.get(1).isDone());
        assertFalse(loadedTasks.get(2).isDone());
        assertEquals(TaskType.TODO, loadedTasks.get(0).getTaskType());
        assertEquals(TaskType.DEADLINE, loadedTasks.get(1).getTaskType());
        assertEquals(TaskType.EVENT, loadedTasks.get(2).getTaskType());
    }

    @Test
    public void save_persistsAcrossSessions_loadsCorrectly() {
        // First session: save tasks
        ArrayList<Task> tasks = new ArrayList<>();
        Task task1 = new Todo("Task 1");
        Task task2 = new Deadline("Task 2", "30/01/2026 1200");
        task1.markAsDone();
        tasks.add(task1);
        tasks.add(task2);
        storage.save(tasks);
        
        // Create new storage instance (simulating new session)
        Storage newStorage = new Storage(testFilePath);
        ArrayList<Task> loadedTasks = newStorage.load();
        
        assertEquals(2, loadedTasks.size());
        assertEquals("Task 1", loadedTasks.get(0).getDescription());
        assertTrue(loadedTasks.get(0).isDone());
        assertEquals("Task 2", loadedTasks.get(1).getDescription());
        assertFalse(loadedTasks.get(1).isDone());
    }

    @Test
    public void save_overwritesExistingFile_updatesCorrectly() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Original task"));
        storage.save(tasks);
        
        ArrayList<Task> loadedTasks = storage.load();
        assertEquals(1, loadedTasks.size());
        
        // Overwrite with new tasks
        ArrayList<Task> newTasks = new ArrayList<>();
        newTasks.add(new Todo("New task 1"));
        newTasks.add(new Todo("New task 2"));
        storage.save(newTasks);
        
        ArrayList<Task> reloadedTasks = storage.load();
        assertEquals(2, reloadedTasks.size());
        assertEquals("New task 1", reloadedTasks.get(0).getDescription());
        assertEquals("New task 2", reloadedTasks.get(1).getDescription());
    }

    @Test
    public void save_createsDirectory_ifNotExists() {
        String nestedPath = tempDir.resolve("nested/dir/hal.txt").toString();
        Storage nestedStorage = new Storage(nestedPath);
        
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Test task"));
        
        nestedStorage.save(tasks);
        
        File file = new File(nestedPath);
        assertTrue(file.exists());
        
        ArrayList<Task> loadedTasks = nestedStorage.load();
        assertEquals(1, loadedTasks.size());
    }

    @Test
    public void save_emptyTaskList_createsEmptyFile() {
        ArrayList<Task> tasks = new ArrayList<>();
        storage.save(tasks);
        
        ArrayList<Task> loadedTasks = storage.load();
        assertEquals(0, loadedTasks.size());
    }
}
