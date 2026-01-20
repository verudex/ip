package hal;

public class Hal {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Hal(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        parser = new Parser();
        loadTasks();
    }

    public Hal() {
        this("./data/hal.txt");
    }

    private void loadTasks() {
        try {
            for (Task task : storage.load()) {
                tasks.addTask(task);
            }
        } catch (HalException e) {
            ui.showError(e.getMessage());
        }
    }

    private void saveTasks() {
        try {
            storage.save(tasks.getAllTasks());
        } catch (HalException e) {
            ui.showError(e.getMessage());
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String input = parser.readCommand();
            isExit = parser.isExit(input);
            
            if (!isExit) {
                try {
                    parser.processCommand(input, tasks, ui, storage);
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
        String filePath = args.length > 0 ? args[0] : "./data/hal.txt";
        new Hal(filePath).run();
    }
}
