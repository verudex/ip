import java.util.Scanner;

public class Hal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String logo = """
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
        String divider = "____________________________________________________________\n";

        System.out.println(divider
                + logo
                + "Hello! I'm Hal\nWhat can I do for you?\n"
                + divider);

        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("bye")) {
            System.out.println(divider + input + "\n" + divider);
            input = scanner.nextLine();
        }

        System.out.println(divider + "Bye. Hope to see you again soon!\n" + divider);
    }
}
