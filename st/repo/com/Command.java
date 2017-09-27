package st.repo.com;

public abstract class Command {
    private final String command;

    Command(String command) {
        this.command = command;
    }

    public abstract void run();

    public static Command getByName(String command) {
        for (Command com : commandList) {
            if (com.command.equals(command)) {
                return com;
            }
        }
        return null;
    }

    private static Command[] commandList = new Command[] {};
}
