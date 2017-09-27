package st.repo.com;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NativeCommand extends Command {
    NativeCommand(String command) {
        super(command);
    }

    @Override
    public void run(File dir, String[] args) {
        try {
            ArrayList<String> commands = new ArrayList<>();
            commands.addAll(Arrays.asList(args));
            commands.add(0, command);
            args = commands.toArray(new String[0]);

            ProcessBuilder pb = new ProcessBuilder(args);
            pb.directory(dir);
            Process p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
