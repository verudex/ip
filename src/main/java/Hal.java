public class Hal {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Hal() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList();
        parser = new Parser();
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String input = parser.readCommand();
            isExit = parser.isExit(input);
            
            if (!isExit) {
                String lowerInput = input.toLowerCase();
                if (lowerInput.equals("list")) {
                    ui.showList(tasks);
                } else if (lowerInput.startsWith("mark ")) {
                    int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                    tasks.markTask(taskIndex);
                    ui.showTaskMarked(tasks.getTask(taskIndex));
                } else if (lowerInput.startsWith("unmark ")) {
                    int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                    tasks.unmarkTask(taskIndex);
                    ui.showTaskUnmarked(tasks.getTask(taskIndex));
                } else {
                    tasks.addTask(input);
                    ui.showTaskAdded(input);
                }
            } else {
                ui.goodbye();
            }
        }
        parser.close();
    }

    public static void main(String[] args) {
        new Hal().run();
    }
}
