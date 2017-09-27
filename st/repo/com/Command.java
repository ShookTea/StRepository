package st.repo.com;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {
    protected final String command;

    Command(String command) {
        this.command = command;
    }

    public abstract void run(File dir, String[] args);

    public static void runCommand(String command, File dir) {
        List<String> parts = new ArrayList<>();
        Matcher m = pattern.matcher(command);
        while (m.find()) {
            parts.add(m.group());
        }

        command = parts.remove(0);
        String[] args = parts.toArray(new String[0]);
        getByName(command).run(dir, args);
    }

    private static Command getByName(String command) {
        for (Command com : commandList) {
            if (com.command.equals(command)) {
                return com;
            }
        }
        return new NativeCommand(command);
    }

    private static Command[] commandList = new Command[] { new UnzipCommand(), new RemoveCommand() };
    private static Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
}
