package st.repo.task;

import javafx.concurrent.Task;
import st.repo.Application;
import st.repo.Start;
import st.repo.reg.Extension;

import java.io.File;
import java.util.Arrays;

public class RemoveTask extends Task {
    private final File folder;
    private final File jsonFile;
    private final long size;
    private final Extension[] extensions;
    private long deleted = 0;

    public RemoveTask(Application app) {
        this.folder = app.installationPath.toFile();
        this.jsonFile = new File(folder.getParent(), app.title.get().replace(" ", "_") + ".strep");
        this.extensions = app.extensions;
        this.size = getSize(folder);
    }

    @Override
    protected Object call() {
        updateProgress(-1, -1);
        updateTitle("Usuwanie...");
        updateMessage("Usuwanie folderu " + folder.getName());
        removeFile(folder);
        updateProgress(-1, -1);
        if (!jsonFile.delete()) jsonFile.deleteOnExit();;
        removeExtensions();
        return null;
    }

    private void removeFile(File file) {
        if (file.isDirectory()) {
            Arrays.stream(file.listFiles()).forEach(this::removeFile);
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
                    .mapToLong(this::getSize)
                    .sum();

        }
        return file.length();
    }

    private void removeExtensions() {
        for (Extension extension : extensions) {
            Start.REGISTRY.removeExtension(extension);
        }
    }
}
