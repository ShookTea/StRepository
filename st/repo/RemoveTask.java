package st.repo;

import javafx.concurrent.Task;

import java.io.File;
import java.util.Arrays;

public class RemoveTask extends Task {
    private final File folder;
    private long size = 0;
    private long deleted = 0;

    public RemoveTask(File folder) {
        this.folder = folder;
    }

    @Override
    protected Object call() {
        updateProgress(-1, -1);
        updateTitle("Usuwanie...");
        updateMessage("Usuwanie folderu " + folder.getName());
        size = getSize(folder);
        removeFile(folder);
        return null;
    }

    private void removeFile(File file) {
        if (file.isDirectory()) {
            Arrays.stream(file.listFiles()).forEach(f -> removeFile(f));
            if (!file.delete()) file.deleteOnExit();
        }
        else {
            deleted += file.length();
            if (!file.delete()) file.deleteOnExit();
            updateProgress(deleted, size);
        }
    }

    private long getSize(File file) {
        if (file.isDirectory()) {
            return Arrays.stream(file.listFiles())
                    .mapToLong(f -> getSize(f))
                    .sum();

        }
        return file.length();
    }
}
