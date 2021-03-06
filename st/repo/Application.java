package st.repo;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import st.repo.com.Command;
import st.repo.reg.Extension;
import st.repo.task.DownloadTask;
import st.repo.task.RemoveTask;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public final StringProperty title;
    public final BooleanProperty isDownloaded;
    public final BooleanProperty canBeUpdated;
    public final BooleanProperty isLocked;
    public final StringProperty installedVersion;
    public final StringProperty description;
    public final ObservableList<Link> authors;
    public final ObservableList<Link> links;

    public final InstallationData installationData;
    public final Path installationPath;
    public final Extension[] extensions;
    private final String[] runningCommand;

    public Application(String title, String description, List<Link> authors, List<Link> links, InstallationData instData, String runningCommand, Extension[] extensions) {
        this.title = new SimpleStringProperty(title);
        this.installedVersion = new SimpleStringProperty("");
        this.description = new SimpleStringProperty(description);
        this.authors = FXCollections.observableArrayList(authors);
        this.links = FXCollections.observableArrayList(links);
        this.isLocked = new SimpleBooleanProperty(false);
        this.installationData = instData;
        this.runningCommand = runningCommand.split(";");
        this.extensions = extensions;

        installationPath = Start.getJarFile().isDirectory() ?
                Paths.get("apps/" + title).toAbsolutePath() :
                new File(Start.getJarFile().getParentFile(), "apps/" + title).toPath();
        isDownloaded = new SimpleBooleanProperty();
        updateDownloadedState();
        canBeUpdated = new SimpleBooleanProperty();
        canBeUpdated.bind(isDownloaded.and(this.installedVersion.isNotEqualTo(this.installationData.newestVersion)));
        checkInstalledVersion();
    }

    private Application() {
        this("", "", new ArrayList<>(), new ArrayList<>(), InstallationData.NULL, "", new Extension[0]);
        this.isLocked.setValue(true);
    }

    public void updateStatus() {
        updateDownloadedState();
        checkInstalledVersion();
    }

    public void checkInstalledVersion() {
        if (!isDownloaded.get() || title.getValue().isEmpty()) return;
        try {
            File installFolder = installationPath.toFile();
            File strepFile = new File(installFolder.getParent(), title.get().replace(" ", "_") + ".strep");
            JSONParser parser = new JSONParser();
            JSONObject parsed = (JSONObject)parser.parse(new FileReader(strepFile));
            String version = (String)parsed.get("version");
            installedVersion.setValue(version);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void runApplication() {
        for (String command : runningCommand) {
            command = command.trim();
            if (!command.isEmpty()) {
                Command.runCommand(command, installationPath.toFile());
            }
        }
    }

    public void runApplication(String command) {
        if (command == null) {
            runApplication();
        }
        else {
            Command.runCommand(command, installationPath.toFile());
        }
    }

    public void updateDownloadedState() {
        isDownloaded.set(installationPath.toFile().exists());
    }

    public Task createDownloadTask() {
        return new DownloadTask(this);
    }

    public Task createRemoveTask() {
        return new RemoveTask(this);
    }

    public static final Application NULL_APP = new Application();
    private static ListProperty<Application> list = new SimpleListProperty<>();

    public static ListProperty<Application> getAppsList() {
        return list;
    }

    public static Application getAppByName(String name) {
        for (Application app : list) {
            if (app.title.get().equals(name)) {
                return app;
            }
        }
        return NULL_APP;
    }

    public static void setAppsList(List<Application> apps) {
        ObservableList<Application> newList = FXCollections.observableArrayList(apps);
        list.setValue(newList);
    }
}
