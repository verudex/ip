package hal;

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
     * Displays a message confirming that a task was marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println(DIVIDER + "Nice! I've marked this task as done:\n  " + task + "\n" + DIVIDER);
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
     * Displays an error message.
     *
     * @param errorMessage The error message to display.
     */
    public void showError(String errorMessage) {
        System.out.println(DIVIDER + errorMessage + "\n" + DIVIDER);
    }
}
