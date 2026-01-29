package hal;

import java.util.ArrayList;

/**
 * Main class for the Hal task manager application.
 */
public class Hal {
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
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        parser = new Parser();
        loadTasks();
    }

    /**
     * Constructs a Hal application with the default file path.
     */
    public Hal() {
        this("./data/hal.txt");
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
        String filePath = args.length > 0 ? args[0] : "./data/hal.txt";
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
        } else if (lowerInput.startsWith("mark")
                && (lowerInput.length() == 4 || lowerInput.charAt(4) == ' ')) {
            return Command.MARK;
        } else if (lowerInput.startsWith("unmark")
                && (lowerInput.length() == 6 || lowerInput.charAt(6) == ' ')) {
            return Command.UNMARK;
        } else if (lowerInput.startsWith("delete")
                && (lowerInput.length() == 6 || lowerInput.charAt(6) == ' ')) {
            return Command.DELETE;
        } else if (lowerInput.startsWith("todo")
                && (lowerInput.length() == 4 || lowerInput.charAt(4) == ' ')) {
            return Command.TODO;
        } else if (lowerInput.startsWith("deadline")
                && (lowerInput.length() == 8 || lowerInput.charAt(8) == ' ')) {
            return Command.DEADLINE;
        } else if (lowerInput.startsWith("event")
                && (lowerInput.length() == 5 || lowerInput.charAt(5) == ' ')) {
            return Command.EVENT;
        } else if (lowerInput.startsWith("find") && (lowerInput.length() == 4 || lowerInput.charAt(4) == ' ')) {
            return Command.FIND;
        } else if (lowerInput.equals("bye")) {
            return Command.BYE;
        } else {
            return Command.UNKNOWN;
        }
    }

    private String handleMarkCommandForGui(String input) {
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
            storage.save(tasks.getAllTasks());
            return ui.getTaskMarked(tasks.getTask(taskIndex));
        } catch (NumberFormatException e) {
            throw new HalException("Error: Invalid task number!");
        }
    }

    private String handleUnmarkCommandForGui(String input) {
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
            storage.save(tasks.getAllTasks());
            return ui.getTaskUnmarked(tasks.getTask(taskIndex));
        } catch (NumberFormatException e) {
            throw new HalException("Error: Invalid task number!");
        }
    }

    private String handleDeleteCommandForGui(String input) {
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
            storage.save(tasks.getAllTasks());
            return ui.getTaskDeleted(deletedTask, tasks.getTaskCount());
        } catch (NumberFormatException e) {
            throw new HalException("Error: Invalid task number!");
        }
    }

    private String handleTodoCommandForGui(String input) {
        String description = input.trim().substring(4).trim();
        if (description.isEmpty()) {
            throw new HalException("Error: Todo description cannot be empty!");
        }
        Task task = new Todo(description);
        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        return ui.getTaskAdded(task, tasks.getTaskCount());
    }

    private String handleDeadlineCommandForGui(String input) {
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
            storage.save(tasks.getAllTasks());
            return ui.getTaskAdded(task, tasks.getTaskCount());
        } catch (Exception e) {
            throw new HalException("Error: Invalid date format! "
                    + "Please use dd/MM/yyyy HHmm format (e.g. 15/10/2019 1800).");
        }
    }

    private String handleEventCommandForGui(String input) {
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
            storage.save(tasks.getAllTasks());
            return ui.getTaskAdded(task, tasks.getTaskCount());
        } catch (Exception e) {
            throw new HalException("Error: Invalid date/time format! "
                    + "Please use dd/MM/yyyy HHmm format (e.g. 15/10/2019 1800).");
        }
    }

    private String handleFindCommandForGui(String input) {
        String keyword = input.trim().substring(4).trim();
        if (keyword.isEmpty()) {
            throw new HalException("Error: Please provide a keyword to search for!");
        }
        ArrayList<Task> foundTasks = tasks.findTasks(keyword);
        return ui.getFoundTasks(foundTasks);
    }
}
