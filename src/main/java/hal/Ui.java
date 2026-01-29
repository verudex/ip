package hal;

import java.util.ArrayList;

/**
 * Handles all user interface interactions.
 */
public class Ui {
    public static final String LOGO = """
                                           .---.\s
                       .                   |   |\s
                     .'|                   |   |\s
                    <  |                   |   |\s
                     | |             __    |   |\s
                     | | .'''-.   .:--.'.  |   |\s
                     | |/.'''. | / |   | | |   |\s
                     |  /    | | `' __ | | |   |\s
                     | |     | |  .'.''| | |   |\s
                     | |     | | / /   | |_'---'\s
                     | '.    | '.| |._,| '/     \s
                     '---'   '---'`--'  `'      \s
                """;
    public static final String DIVIDER = "____________________________________________________________\n";

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        System.out.println(DIVIDER
                + "Hello! I'm Hal\nWhat can I do for you?\n"
                + DIVIDER);
    }

    /**
     * Returns the welcome message.
     *
     * @return The welcome message string.
     */
    public String getWelcome() {
        return "Hello! I'm Hal\nWhat can I do for you?\n";
    }

    /**
     * Displays the input string with dividers.
     *
     * @param input The string to display.
     */
    public void echo(String input) {
        System.out.println(Ui.DIVIDER + input + "\n" + Ui.DIVIDER);
    }

    /**
     * Displays the goodbye message.
     */
    public void goodbye() {
        System.out.println(Ui.DIVIDER + "Bye. Hope to see you again soon!\n" + Ui.DIVIDER);
    }

    /**
     * Returns the goodbye message.
     *
     * @return The goodbye message string.
     */
    public String getGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays a message confirming that a task was added.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println(DIVIDER + "Got it. I've added this task:\n  " + task 
                + "\nNow you have " + totalTasks + " tasks in the list.\n" + DIVIDER);
    }

    /**
     * Returns a message confirming that a task was added.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks.
     * @return The task added confirmation message.
     */
    public String getTaskAdded(Task task, int totalTasks) {
        return "Got it. I've added this task:\n  " + task 
                + "\nNow you have " + totalTasks + " tasks in the list.";
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The task list to display.
     */
    public void showList(TaskList tasks) {
        System.out.print(DIVIDER + "Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
        }
        System.out.println(DIVIDER);
    }

    /**
     * Returns the list of tasks as a formatted string.
     *
     * @param tasks The task list to format.
     * @return The formatted task list string.
     */
    public String getList(TaskList tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.getTask(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Displays a message confirming that a task was marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println(DIVIDER + "Nice! I've marked this task as done:\n  " + task + "\n" + DIVIDER);
    }

    /**
     * Returns a message confirming that a task was marked as done.
     *
     * @param task The task that was marked.
     * @return The task marked confirmation message.
     */
    public String getTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Displays a message confirming that a task was unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(DIVIDER + "OK, I've marked this task as not done yet:\n  " + task + "\n" + DIVIDER);
    }

    /**
     * Returns a message confirming that a task was unmarked.
     *
     * @param task The task that was unmarked.
     * @return The task unmarked confirmation message.
     */
    public String getTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Displays a message confirming that a task was deleted.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of remaining tasks.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println(DIVIDER + "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + totalTasks + " tasks in the list.\n" + DIVIDER);
    }

    /**
     * Returns a message confirming that a task was deleted.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of remaining tasks.
     * @return The task deleted confirmation message.
     */
    public String getTaskDeleted(Task task, int totalTasks) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + totalTasks + " tasks in the list.";
    }

    /**
     * Displays an error message.
     *
     * @param errorMessage The error message to display.
     */
    public void showError(String errorMessage) {
        System.out.println(DIVIDER + errorMessage + "\n" + DIVIDER);
    }

    /**
     * Displays multiple error messages.
     *
     * @param errorMessages The error messages to display.
     */
    public void showErrors(String... errorMessages) {
        StringBuilder sb = new StringBuilder(DIVIDER);
        for (String error : errorMessages) {
            sb.append(error).append("\n");
        }
        sb.append(DIVIDER);
        System.out.print(sb.toString());
    }

    /**
     * Displays the list of tasks found by search.
     *
     * @param tasks The list of found tasks.
     */
    public void showFoundTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(DIVIDER + "No matching tasks found.\n" + DIVIDER);
        } else {
            System.out.print(DIVIDER + "Here are the matching tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
            System.out.println(DIVIDER);
        }
    }

    /**
     * Returns the list of tasks found by search as a formatted string.
     *
     * @param tasks The list of found tasks.
     * @return The formatted found tasks string.
     */
    public String getFoundTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No matching tasks found.";
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
            return sb.toString().trim();
        }
    }

    /**
     * Displays multiple tasks using varargs.
     *
     * @param tasks The tasks to display.
     */
    public void showTasks(Task... tasks) {
        System.out.print(DIVIDER + "Tasks:\n");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
        System.out.println(DIVIDER);
    }

    /**
     * Returns a formatted string of multiple tasks using varargs.
     *
     * @param tasks The tasks to format.
     * @return The formatted string.
     */
    public String getTasks(Task... tasks) {
        if (tasks.length == 0) {
            return "No tasks.";
        }
        StringBuilder sb = new StringBuilder("Tasks:\n");
        for (int i = 0; i < tasks.length; i++) {
            sb.append((i + 1)).append(". ").append(tasks[i]).append("\n");
        }
        return sb.toString().trim();
    }
}
