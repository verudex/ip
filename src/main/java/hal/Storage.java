package hal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving of tasks to a file.
 */
public class Storage {
    private static final String TASK_SEPARATOR = " | ";
    private static final String DONE_MARKER = "1";
    private static final String TODO_TYPE = "T";
    private static final String DEADLINE_TYPE = "D";
    private static final String EVENT_TYPE = "E";
    private static final int MIN_TASK_PARTS = 3;
    private static final int DEADLINE_PARTS = 4;
    private static final int EVENT_PARTS = 5;
    private static final int TYPE_INDEX = 0;
    private static final int STATUS_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int DEADLINE_TIME_INDEX = 3;
    private static final int EVENT_FROM_INDEX = 3;
    private static final int EVENT_TO_INDEX = 4;
    
    private String filePath;

    /**
     * Constructs a Storage with the default file path.
     */
    public Storage() {
        this.filePath = "./data/hal.txt";
    }

    /**
     * Constructs a Storage with the specified file path.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        assert filePath != null : "File path should not be null";
        assert !filePath.trim().isEmpty() : "File path should not be empty";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return The list of loaded tasks.
     * @throws HalException If the file cannot be loaded.
     */
    public ArrayList<Task> load() throws HalException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new HalException("Error: Could not load data file!");
        }

        return tasks;
    }

    private Task parseTask(String line) {
        String[] parts = line.split(TASK_SEPARATOR);
        if (parts.length < MIN_TASK_PARTS) {
            return null;
        }

        String type = parts[TYPE_INDEX];
        boolean isDone = parts[STATUS_INDEX].equals(DONE_MARKER);
        String description = parts[DESCRIPTION_INDEX];

        Task task = createTaskByType(type, parts, description);

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }

    private Task createTaskByType(String type, String[] parts, String description) {
        try {
            if (type.equals(TODO_TYPE)) {
                return new Todo(description);
            } else if (type.equals(DEADLINE_TYPE) && parts.length >= DEADLINE_PARTS) {
                return new Deadline(description, LocalDateTime.parse(parts[DEADLINE_TIME_INDEX]));
            } else if (type.equals(EVENT_TYPE) && parts.length >= EVENT_PARTS) {
                return new Event(description, 
                        LocalDateTime.parse(parts[EVENT_FROM_INDEX]), 
                        LocalDateTime.parse(parts[EVENT_TO_INDEX]));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Saves tasks to the storage file.
     *
     * @param tasks The tasks to save (can be an ArrayList or individual tasks).
     * @throws HalException If the file cannot be saved.
     */
    public void save(Task... tasks) throws HalException {
        try {
            File file = new File(filePath);
            File directory = file.getParentFile();
            
            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new HalException("Error: Could not save data to file!");
        }
    }

    /**
     * Saves tasks from an ArrayList to the storage file.
     *
     * @param taskList The list of tasks to save.
     * @throws HalException If the file cannot be saved.
     */
    public void save(ArrayList<Task> taskList) throws HalException {
        save(taskList.toArray(new Task[0]));
    }
}
