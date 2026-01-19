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
        String lowerInput = input.toLowerCase();
        
        if (lowerInput.equals("list")) {
            ui.showList(tasks);
        } else if (lowerInput.startsWith("mark ")) {
            int taskIndex = Integer.parseInt(input.substring(5).trim()) - 1;
            tasks.markTask(taskIndex);
            ui.showTaskMarked(tasks.getTask(taskIndex));
        } else if (lowerInput.startsWith("unmark ")) {
            int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
            tasks.unmarkTask(taskIndex);
            ui.showTaskUnmarked(tasks.getTask(taskIndex));
        } else if (lowerInput.startsWith("todo ")) {
            String description = input.substring(5).trim();
            Task task = new Todo(description);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
        } else if (lowerInput.startsWith("deadline ")) {
            String[] parts = input.substring(9).split(" /by ");
            String description = parts[0].trim();
            String by = parts[1].trim();
            Task task = new Deadline(description, by);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
        } else if (lowerInput.startsWith("event ")) {
            String[] parts = input.substring(6).split(" /from | /to ");
            String description = parts[0].trim();
            String from = parts[1].trim();
            String to = parts[2].trim();
            Task task = new Event(description, from, to);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
        } else {
            Task task = new Task(input);
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getTaskCount());
        }
    }

    public void close() {
        scanner.close();
    }
}
