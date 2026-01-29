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
}
