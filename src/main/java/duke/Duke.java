package duke;

import duke.command.Parser;
import duke.exception.DukeException;
import duke.io.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Duke class encapsulates a Duke program. It contains the
 * initialization of the Duke bot, as well as the methods
 * required to parse and return user input.
 */
public class Duke {
    private final Storage STORAGE;
    private final Ui UI;
    private TaskList taskList;

    /**
     * Constructor for a Duke bot. It also initializes the
     * UI, Storage and tries to load an existing Task List.
     */
    public Duke() {
        this.UI = new Ui();
        this.STORAGE = new Storage("/data/duke.txt");

        try {
            /* try loading text file from storage */
            this.taskList = new TaskList(STORAGE.load());
        } catch (DukeException de) {
            this.taskList = new TaskList();
        }
    }

    /**
     * Takes in a user input, parses it and returns a response
     *
     * @param input user input in the form of a command
     * @return the response with regard to the input
     */
    public String getResponse(String input) {
        String response = "";

        try{
            response = new Parser(input, this.taskList, this.STORAGE, this.UI).compute();
        } catch (DukeException de) {
            response = de.toString();
        }

        return response;
    }

    /**
     * Returns a welcome message to the user
     *
     * @return Duke's welcome message
     */
    public String welcomeMessage() {
        return this.UI.welcomeMessage();
    }
}
