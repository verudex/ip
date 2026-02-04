package hal;

import java.util.ArrayList;

/**
 * Main class for the Hal task manager application.
 */
public class Hal {
    private static final String DEFAULT_FILE_PATH = "./data/hal.txt";
    private static final int MARK_COMMAND_LENGTH = 4;
    private static final int UNMARK_COMMAND_LENGTH = 6;
    private static final int DELETE_COMMAND_LENGTH = 6;
    private static final int TODO_COMMAND_LENGTH = 4;
    private static final int DEADLINE_COMMAND_LENGTH = 8;
    private static final int EVENT_COMMAND_LENGTH = 5;
    private static final int FIND_COMMAND_LENGTH = 4;
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Constructs a Hal application with the specified file path.
     *
     * @param filePath The path to the storage file.
     */
    public Hal(String filePath) {
        assert filePath != null : "File path should not be null";
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        parser = new Parser();
        assert ui != null && storage != null && tasks != null && parser != null 
                : "All components should be initialized";
        loadTasks();
    }

    /**
     * Constructs a Hal application with the default file path.
     */
    public Hal() {
        this(DEFAULT_FILE_PATH);
    }

    private void loadTasks() {
        try {
            for (Task task : storage.load()) {
                tasks.addTask(task);
            }
        } catch (HalException e) {
            ui.showError(e.getMessage());
        }
    }

    private void saveTasks() {
        try {
            storage.save(tasks.getAllTasks());
        } catch (HalException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Runs the main application loop.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String input = parser.readCommand();
            isExit = parser.isExit(input);
            
            if (!isExit) {
                try {
                    parser.processCommand(input, tasks, ui, storage);
                } catch (HalException e) {
                    ui.showError(e.getMessage());
                }
            } else {
                ui.goodbye();
            }
        }
        parser.close();
    }

    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : DEFAULT_FILE_PATH;
        new Hal(filePath).run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        if (parser.isExit(input)) {
            return ui.getGoodbye();
        }
        try {
            return processCommandForGui(input);
        } catch (HalException e) {
            return e.getMessage();
        }
    }

    /**
     * Returns the welcome message.
     *
     * @return The welcome message string.
     */
    public String getWelcomeMessage() {
        return ui.getWelcome();
    }

    private String processCommandForGui(String input) {
        Command command = parseCommand(input);
        
        switch (command) {
        case LIST:
            return ui.getList(tasks);
        case MARK:
            return handleMarkCommandForGui(input);
        case UNMARK:
            return handleUnmarkCommandForGui(input);
        case DELETE:
            return handleDeleteCommandForGui(input);
        case TODO:
            return handleTodoCommandForGui(input);
        case DEADLINE:
            return handleDeadlineCommandForGui(input);
        case EVENT:
            return handleEventCommandForGui(input);
        case FIND:
            return handleFindCommandForGui(input);
        default:
            throw new HalException("Error: Please use a valid command!");
        }
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

    private int parseAndValidateTaskIndex(String indexStr) throws HalException {
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

    private String handleMarkCommandForGui(String input) {
        String indexStr = input.trim().substring(MARK_COMMAND_LENGTH).trim();
        int taskIndex = parseAndValidateTaskIndex(indexStr);
        tasks.markTask(taskIndex);
        storage.save(tasks.getAllTasks());
        return ui.getTaskMarked(tasks.getTask(taskIndex));
    }

    private String handleUnmarkCommandForGui(String input) {
        String indexStr = input.trim().substring(UNMARK_COMMAND_LENGTH).trim();
        int taskIndex = parseAndValidateTaskIndex(indexStr);
        tasks.unmarkTask(taskIndex);
        storage.save(tasks.getAllTasks());
        return ui.getTaskUnmarked(tasks.getTask(taskIndex));
    }

    private String handleDeleteCommandForGui(String input) {
        String indexStr = input.trim().substring(DELETE_COMMAND_LENGTH).trim();
        int taskIndex = parseAndValidateTaskIndex(indexStr);
        Task deletedTask = tasks.deleteTask(taskIndex);
        storage.save(tasks.getAllTasks());
        return ui.getTaskDeleted(deletedTask, tasks.getTaskCount());
    }

    private String handleTodoCommandForGui(String input) {
        String description = input.trim().substring(TODO_COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            throw new HalException("Error: Todo description cannot be empty!");
        }
        Task task = new Todo(description);
        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        return ui.getTaskAdded(task, tasks.getTaskCount());
    }

    private static final String DEADLINE_SEPARATOR = " /by";
    private static final String EVENT_FROM_SEPARATOR = " /from";
    private static final String EVENT_TO_SEPARATOR = " /to";
    private static final int DEADLINE_SEPARATOR_LENGTH = 4;
    private static final int EVENT_FROM_SEPARATOR_LENGTH = 6;
    private static final int EVENT_TO_SEPARATOR_LENGTH = 4;
    
    private String handleDeadlineCommandForGui(String input) {
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
        
        validateNonEmptyDescription(description, "Deadline");
        if (by.isEmpty()) {
            throw new HalException("Error: Deadline time cannot be empty!");
        }
        
        return createAndAddTask(new Deadline(description, by));
    }

    private void validateNonEmptyDescription(String description, String taskType) {
        if (description.isEmpty()) {
            throw new HalException("Error: " + taskType + " description cannot be empty!");
        }
    }

    private String createAndAddTask(Task task) {
        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        return ui.getTaskAdded(task, tasks.getTaskCount());
    }

    private String handleEventCommandForGui(String input) {
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
        
        validateNonEmptyDescription(description, "Event");
        if (from.isEmpty() || to.isEmpty()) {
            throw new HalException("Error: Event time cannot be empty!");
        }
        
        return createAndAddTask(new Event(description, from, to));
    }

    private String handleFindCommandForGui(String input) {
        String keyword = input.trim().substring(FIND_COMMAND_LENGTH).trim();
        if (keyword.isEmpty()) {
            throw new HalException("Error: Please provide a keyword to search for!");
        }
        ArrayList<Task> foundTasks = tasks.findTasks(keyword);
        return ui.getFoundTasks(foundTasks);
    }
}
