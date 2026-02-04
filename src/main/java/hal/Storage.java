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
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;
        try {
            if (type.equals("T")) {
                task = new Todo(description);
            } else if (type.equals("D")) {
                if (parts.length >= 4) {
                    task = new Deadline(description, LocalDateTime.parse(parts[3]));
                }
            } else if (type.equals("E")) {
                if (parts.length >= 5) {
                    task = new Event(description, LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
                }
            }
        } catch (Exception e) {
            return null;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
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
