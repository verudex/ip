import java.util.Scanner;

public class Parser {
    private Scanner scanner;

    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public boolean isExit(String input) {
        return input.equalsIgnoreCase("bye");
    }

    public void processCommand(String input, TaskList tasks, Ui ui) {
        String lowerInput = input.toLowerCase().trim();
        
        if (lowerInput.equals("list")) {
            ui.showList(tasks);
        } else if (lowerInput.startsWith("mark") && (lowerInput.length() == 4 || lowerInput.charAt(4) == ' ')) {
            String indexStr = input.trim().substring(4).trim();
            if (indexStr.isEmpty()) {
                throw new HalException("Error: Task number is required for the mark command!");
            }
            try {
                int taskIndex = Integer.parseInt(indexStr) - 1;
                if (taskIndex < 0 || taskIndex >= tasks.getTaskCount()) {
                    throw new HalException("Error: Task number is out of range of task list!");
                }
                tasks.markTask(taskIndex);
                ui.showTaskMarked(tasks.getTask(taskIndex));
            } catch (NumberFormatException e) {
                throw new HalException("Error: Invalid task number!");
            }
        } else if (lowerInput.startsWith("unmark") && (lowerInput.length() == 6 || lowerInput.charAt(6) == ' ')) {
            String indexStr = input.trim().substring(6).trim();
            if (indexStr.isEmpty()) {
                throw new HalException("Error: Task number is required for the unmark command!");
            }
            try {
                int taskIndex = Integer.parseInt(indexStr) - 1;
                if (taskIndex < 0 || taskIndex >= tasks.getTaskCount()) {
                    throw new HalException("Error: Task number is out of range of task list!");
                }
                tasks.unmarkTask(taskIndex);
                ui.showTaskUnmarked(tasks.getTask(taskIndex));
            } catch (NumberFormatException e) {
                throw new HalException("Error: Invalid task number!");
            }
        } else if (lowerInput.startsWith("delete") && (lowerInput.length() == 6 || lowerInput.charAt(6) == ' ')) {
            String indexStr = input.trim().substring(6).trim();
            if (indexStr.isEmpty()) {
                throw new HalException("Error: Task number is required for the delete command!");
            }
            try {
                int taskIndex = Integer.parseInt(indexStr) - 1;
                if (taskIndex < 0 || taskIndex >= tasks.getTaskCount()) {
                    throw new HalException("Error: Task number is out of range of task list!");
                }
                Task deletedTask = tasks.deleteTask(taskIndex);
                ui.showTaskDeleted(deletedTask, tasks.getTaskCount());
            } catch (NumberFormatException e) {
                throw new HalException("Error: Invalid task number!");
            }
        } else if (lowerInput.startsWith("todo") && (lowerInput.length() == 4 || lowerInput.charAt(4) == ' ')) {
            String description = input.trim().substring(4).trim();
            if (description.isEmpty()) {
                throw new HalException("Error: Todo description cannot be empty!");
            }
            Task task = new Todo(description);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
        } else if (lowerInput.startsWith("deadline") && (lowerInput.length() == 8 || lowerInput.charAt(8) == ' ')) {
            String details = input.trim().substring(8).trim();
            if (details.isEmpty()) {
                throw new HalException("Error: Deadline description cannot be empty!");
            }
            int byIndex = details.indexOf(" /by");
            if (byIndex == -1) {
                throw new HalException("Error: Deadline requires /by to be used properly!");
            }
            String description = details.substring(0, byIndex).trim();
            String by = details.substring(byIndex + 4).trim();
            if (description.isEmpty()) {
                throw new HalException("Error: Deadline description cannot be empty!");
            }
            if (by.isEmpty()) {
                throw new HalException("Error: Deadline time cannot be empty!");
            }
            Task task = new Deadline(description, by);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
        } else if (lowerInput.startsWith("event") && (lowerInput.length() == 5 || lowerInput.charAt(5) == ' ')) {
            String details = input.trim().substring(5).trim();
            if (details.isEmpty()) {
                throw new HalException("Error: Event description cannot be empty!");
            }
            int fromIndex = details.indexOf(" /from");
            int toIndex = details.indexOf(" /to");
            if (fromIndex == -1 || toIndex == -1) {
                throw new HalException("Error: Event requires /from and /to to be used properly!");
            }
            String description = details.substring(0, fromIndex).trim();
            String from = details.substring(fromIndex + 6, toIndex).trim();
            String to = details.substring(toIndex + 4).trim();
            if (description.isEmpty()) {
                throw new HalException("Error: Event description cannot be empty!");
            }
            if (from.isEmpty() || to.isEmpty()) {
                throw new HalException("Error: Event time cannot be empty!");
            }
            Task task = new Event(description, from, to);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
        } else {
            throw new HalException("Error: Please use a valid command!");
        }
    }

    public void close() {
        scanner.close();
    }
}
