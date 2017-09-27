package st.repo.com;

import java.io.File;

public abstract class Command {
    private final String command;

    Command(String command) {
        this.command = command;
    }

    public abstract void run();

    public static void runCommand(String command, File dir) {

    }

    private static Command getByName(String command) {
        for (Command com : commandList) {
            if (com.command.equals(command)) {
                return com;
            }
        }
        return null;
    }

    private static Command[] commandList = new Command[] {};
}
