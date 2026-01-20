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
                try {
                    parser.processCommand(input, tasks, ui);
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
        new Hal().run();
    }
}
