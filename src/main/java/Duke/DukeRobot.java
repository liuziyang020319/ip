package Duke;

import Duke.DukeCommandLine.DukeCommandLineInput;
import Duke.DukeCommandLine.DukeParser;
import Duke.DukeCommandLine.DukeTaskInputException;
import Duke.DukeFunction.DukeList;
import Duke.DukeFunction.DukeStorage;
import Duke.DukeFunction.DukeUI;

public class DukeRobot {
    private DukeUI ui;
    private DukeList tasks;
    private DukeParser parser;
    private DukeStorage storage;
    public DukeRobot() {
        this.ui = new DukeUI();
        this.tasks = new DukeList();
        this.parser = new DukeParser();
        this.storage = new DukeStorage();
        ui.printGreeting();
        try {
            System.out.println("Loading tasks from file...");
            storage.loadTask(tasks);
        } catch (DukeTaskInputException e) {
            ui.printError(e.getMessage());
        }
        ui.printLine();
    }

    public void run() {
        boolean isExit = false;
        while(!isExit) {
            try {
                String fullCommand  = ui.readCommand();
                DukeCommandLineInput command = parser.parse(fullCommand);
                command.execute(tasks, ui, parser, storage);
                isExit = command.isExit();
            } catch (DukeTaskInputException e) {
                ui.printError(e.getMessage());
            } finally {
                ui.printLine();
            }
        }
    }

    public static void main(String[] args) {
        new DukeRobot().run();
    }
}
