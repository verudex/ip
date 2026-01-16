public class Hal {
    public static void main(String[] args) {
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
                + divider
                + "Bye. Hope to see you again soon!\n"
                + divider);
    }
}
