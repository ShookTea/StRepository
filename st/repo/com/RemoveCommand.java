package st.repo.com;

import java.io.File;

public class RemoveCommand extends Command {
    RemoveCommand() {
        super("remove");
    }

    @Override
    public void run(File dir, String[] args) {
        if (args.length == 0) return;
        File toRemove = new File(dir, args[0]);
        if (!toRemove.delete()) toRemove.deleteOnExit();
    }
}
