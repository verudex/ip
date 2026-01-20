package hal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage() {
        this.filePath = "./data/hal.txt";
    }

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length >= 4) {
                    task = new Deadline(description, LocalDateTime.parse(parts[3]));
                }
                break;
            case "E":
                if (parts.length >= 5) {
                    task = new Event(description, LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
                }
                break;
            }
        } catch (Exception e) {
            return null;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }

    public void save(ArrayList<Task> tasks) throws HalException {
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
}
