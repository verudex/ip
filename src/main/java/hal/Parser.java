package hal;

import java.util.Scanner;

/**
 * Handles parsing and processing of user commands.
 */
public class Parser {
    private Scanner scanner;

    /**
     * Constructs a Parser with a scanner for reading input.
     */
    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user.
     *
     * @return The user input string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Checks if the input is an exit command.
     *
     * @param input The user input.
     * @return True if the input is "bye", false otherwise.
     */
    public boolean isExit(String input) {
        return input.equalsIgnoreCase("bye");
    }

    private Command parseCommand(String input) {
        String lowerInput = input.toLowerCase().trim();
        
        if (lowerInput.equals("list")) {
            return Command.LIST;
        } else if (lowerInput.startsWith("mark") && (lowerInput.length() == 4 || lowerInput.charAt(4) == ' ')) {
            return Command.MARK;
        } else if (lowerInput.startsWith("unmark") && (lowerInput.length() == 6 || lowerInput.charAt(6) == ' ')) {
            return Command.UNMARK;
        } else if (lowerInput.startsWith("delete") && (lowerInput.length() == 6 || lowerInput.charAt(6) == ' ')) {
            return Command.DELETE;
        } else if (lowerInput.startsWith("todo") && (lowerInput.length() == 4 || lowerInput.charAt(4) == ' ')) {
            return Command.TODO;
        } else if (lowerInput.startsWith("deadline") && (lowerInput.length() == 8 || lowerInput.charAt(8) == ' ')) {
            return Command.DEADLINE;
        } else if (lowerInput.startsWith("event") && (lowerInput.length() == 5 || lowerInput.charAt(5) == ' ')) {
            return Command.EVENT;
        } else if (lowerInput.equals("bye")) {
            return Command.BYE;
        } else {
            return Command.UNKNOWN;
        }
    }

    /**
     * Processes a user command and executes the corresponding action.
     *
     * @param input The user input string.
     * @param tasks The task list to operate on.
     * @param ui The UI for displaying messages.
     * @param storage The storage for saving tasks.
     */
    public void processCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        Command command = parseCommand(input);
        
        switch (command) {
        case LIST:
            ui.showList(tasks);
            break;
        case MARK:
            handleMarkCommand(input, tasks, ui, storage);
            break;
        case UNMARK:
            handleUnmarkCommand(input, tasks, ui, storage);
            break;
        case DELETE:
            handleDeleteCommand(input, tasks, ui, storage);
            break;
        case TODO:
            handleTodoCommand(input, tasks, ui, storage);
            break;
        case DEADLINE:
            handleDeadlineCommand(input, tasks, ui, storage);
            break;
        case EVENT:
            handleEventCommand(input, tasks, ui, storage);
            break;
        default:
            throw new HalException("Error: Please use a valid command!");
        }
    }

    private void handleMarkCommand(String input, TaskList tasks, Ui ui, Storage storage) {
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
            storage.save(tasks.getAllTasks());
        } catch (NumberFormatException e) {
            throw new HalException("Error: Invalid task number!");
        }
    }

    private void handleUnmarkCommand(String input, TaskList tasks, Ui ui, Storage storage) {
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
            storage.save(tasks.getAllTasks());
        } catch (NumberFormatException e) {
            throw new HalException("Error: Invalid task number!");
        }
    }

    private void handleDeleteCommand(String input, TaskList tasks, Ui ui, Storage storage) {
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
            storage.save(tasks.getAllTasks());
        } catch (NumberFormatException e) {
            throw new HalException("Error: Invalid task number!");
        }
    }

    private void handleTodoCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String description = input.trim().substring(4).trim();
        if (description.isEmpty()) {
            throw new HalException("Error: Todo description cannot be empty!");
        }
        Task task = new Todo(description);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getTaskCount());
        storage.save(tasks.getAllTasks());
    }

    private void handleDeadlineCommand(String input, TaskList tasks, Ui ui, Storage storage) {
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
        try {
            Task task = new Deadline(description, by);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
            storage.save(tasks.getAllTasks());
        } catch (Exception e) {
            throw new HalException("Error: Invalid date format! Please use dd/MM/yyyy HHmm format (e.g. 15/10/2019 1800).");
        }
    }

    private void handleEventCommand(String input, TaskList tasks, Ui ui, Storage storage) {
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
        try {
            Task task = new Event(description, from, to);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
            storage.save(tasks.getAllTasks());
        } catch (Exception e) {
            throw new HalException("Error: Invalid date/time format! Please use dd/MM/yyyy HHmm format (e.g. 15/10/2019 1800).");
        }
    }

    /**
     * Closes the scanner and releases resources.
     */
    public void close() {
        scanner.close();
    }
}
