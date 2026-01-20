package hal;

import java.util.ArrayList;

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

    public void showWelcome() {
        System.out.println(DIVIDER
                + "Hello! I'm Hal\nWhat can I do for you?\n"
                + DIVIDER);
    }

    public void echo(String input) {
        System.out.println(Ui.DIVIDER + input + "\n" + Ui.DIVIDER);
    }

    public void goodbye() {
        System.out.println(Ui.DIVIDER + "Bye. Hope to see you again soon!\n" + Ui.DIVIDER);
    }

    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println(DIVIDER + "Got it. I've added this task:\n  " + task 
                + "\nNow you have " + totalTasks + " tasks in the list.\n" + DIVIDER);
    }

    public void showList(TaskList tasks) {
        System.out.print(DIVIDER + "Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getTaskCount(); i++) {
            System.out.println((i + 1) + "." + tasks.getTask(i));
        }
        System.out.println(DIVIDER);
    }

    public void showTaskMarked(Task task) {
        System.out.println(DIVIDER + "Nice! I've marked this task as done:\n  " + task + "\n" + DIVIDER);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(DIVIDER + "OK, I've marked this task as not done yet:\n  " + task + "\n" + DIVIDER);
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println(DIVIDER + "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + totalTasks + " tasks in the list.\n" + DIVIDER);
    }

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
}
