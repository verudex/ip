package hal;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles parsing and processing of user commands.
 */
public class Parser {
    private static final int MARK_COMMAND_LENGTH = 4;
    private static final int UNMARK_COMMAND_LENGTH = 6;
    private static final int DELETE_COMMAND_LENGTH = 6;
    private static final int TODO_COMMAND_LENGTH = 4;
    private static final int DEADLINE_COMMAND_LENGTH = 8;
    private static final int EVENT_COMMAND_LENGTH = 5;
    private static final int FIND_COMMAND_LENGTH = 4;
    private static final String DEADLINE_SEPARATOR = " /by";
    private static final String EVENT_FROM_SEPARATOR = " /from";
    private static final String EVENT_TO_SEPARATOR = " /to";
    private static final int DEADLINE_SEPARATOR_LENGTH = 4;
    private static final int EVENT_FROM_SEPARATOR_LENGTH = 6;
    private static final int EVENT_TO_SEPARATOR_LENGTH = 4;
    
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
        } else if (isCommandWithOptionalSpace(lowerInput, "mark", MARK_COMMAND_LENGTH)) {
            return Command.MARK;
        } else if (isCommandWithOptionalSpace(lowerInput, "unmark", UNMARK_COMMAND_LENGTH)) {
            return Command.UNMARK;
        } else if (isCommandWithOptionalSpace(lowerInput, "delete", DELETE_COMMAND_LENGTH)) {
            return Command.DELETE;
        } else if (isCommandWithOptionalSpace(lowerInput, "todo", TODO_COMMAND_LENGTH)) {
            return Command.TODO;
        } else if (isCommandWithOptionalSpace(lowerInput, "deadline", DEADLINE_COMMAND_LENGTH)) {
            return Command.DEADLINE;
        } else if (isCommandWithOptionalSpace(lowerInput, "event", EVENT_COMMAND_LENGTH)) {
            return Command.EVENT;
        } else if (isCommandWithOptionalSpace(lowerInput, "find", FIND_COMMAND_LENGTH)) {
            return Command.FIND;
        } else if (lowerInput.equals("bye")) {
            return Command.BYE;
        } else {
            return Command.UNKNOWN;
        }
    }

    private boolean isCommandWithOptionalSpace(String input, String command, int commandLength) {
        return input.startsWith(command) 
                && (input.length() == commandLength || input.charAt(commandLength) == ' ');
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
        case FIND:
            handleFindCommand(input, tasks, ui);
            break;
        default:
            throw new HalException("Error: Please use a valid command!");
        }
    }

    private void handleMarkCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String indexStr = input.trim().substring(MARK_COMMAND_LENGTH).trim();
        int taskIndex = parseAndValidateTaskIndex(indexStr, tasks);
        tasks.markTask(taskIndex);
        ui.showTaskMarked(tasks.getTask(taskIndex));
        storage.save(tasks.getAllTasks());
    }

    private void handleUnmarkCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String indexStr = input.trim().substring(UNMARK_COMMAND_LENGTH).trim();
        int taskIndex = parseAndValidateTaskIndex(indexStr, tasks);
        tasks.unmarkTask(taskIndex);
        ui.showTaskUnmarked(tasks.getTask(taskIndex));
        storage.save(tasks.getAllTasks());
    }

    private void handleDeleteCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String indexStr = input.trim().substring(DELETE_COMMAND_LENGTH).trim();
        int taskIndex = parseAndValidateTaskIndex(indexStr, tasks);
        Task deletedTask = tasks.deleteTask(taskIndex);
        ui.showTaskDeleted(deletedTask, tasks.getTaskCount());
        storage.save(tasks.getAllTasks());
    }

    private void handleTodoCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String description = input.trim().substring(TODO_COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            throw new HalException("Error: Todo description cannot be empty!");
        }
        Task task = new Todo(description);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getTaskCount());
        storage.save(tasks.getAllTasks());
    }

    private void handleDeadlineCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String details = input.trim().substring(DEADLINE_COMMAND_LENGTH).trim();
        if (details.isEmpty()) {
            throw new HalException("Error: Deadline description cannot be empty!");
        }
        int byIndex = details.indexOf(DEADLINE_SEPARATOR);
        if (byIndex == -1) {
            throw new HalException("Error: Deadline requires /by to be used properly!");
        }
        String description = details.substring(0, byIndex).trim();
        String by = details.substring(byIndex + DEADLINE_SEPARATOR_LENGTH).trim();
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
            throw new HalException("Error: Invalid date format! "
                    + "Please use dd/MM/yyyy HHmm format (e.g. 15/10/2019 1800).");
        }
    }

    private void handleEventCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String details = input.trim().substring(EVENT_COMMAND_LENGTH).trim();
        if (details.isEmpty()) {
            throw new HalException("Error: Event description cannot be empty!");
        }
        int fromIndex = details.indexOf(EVENT_FROM_SEPARATOR);
        int toIndex = details.indexOf(EVENT_TO_SEPARATOR);
        if (fromIndex == -1 || toIndex == -1) {
            throw new HalException("Error: Event requires /from and /to to be used properly!");
        }
        String description = details.substring(0, fromIndex).trim();
        String from = details.substring(fromIndex + EVENT_FROM_SEPARATOR_LENGTH, toIndex).trim();
        String to = details.substring(toIndex + EVENT_TO_SEPARATOR_LENGTH).trim();
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
            throw new HalException("Error: Invalid date/time format! "
                    + "Please use dd/MM/yyyy HHmm format (e.g. 15/10/2019 1800).");
        }
    }

    private void handleFindCommand(String input, TaskList tasks, Ui ui) {
        String keyword = input.trim().substring(FIND_COMMAND_LENGTH).trim();
        if (keyword.isEmpty()) {
            throw new HalException("Error: Please provide a keyword to search for!");
        }
        ArrayList<Task> foundTasks = tasks.findTasks(keyword);
        ui.showFoundTasks(foundTasks);
    }

    /**
     * Closes the scanner and releases resources.
     */
    public void close() {
        scanner.close();
    }

    private int parseAndValidateTaskIndex(String indexStr, TaskList tasks) throws HalException {
        if (indexStr.isEmpty()) {
            throw new HalException("Error: Task number is required!");
        }
        try {
            int taskIndex = Integer.parseInt(indexStr) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.getTaskCount()) {
                throw new HalException("Error: Task number is out of range of task list!");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new HalException("Error: Invalid task number!");
        }
    }
}
