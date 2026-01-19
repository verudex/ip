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

    public void close() {
        scanner.close();
    }
}
