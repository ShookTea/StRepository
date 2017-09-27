package st.repo;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public Application(String title, String installedVersion, String description, List<Link> authors, List<Link> links, InstallationData instData) {
        this.title = new SimpleStringProperty(title);
        this.installedVersion = new SimpleStringProperty(installedVersion);
        this.description = new SimpleStringProperty(description);
        this.authors = FXCollections.observableArrayList(authors);
        this.links = FXCollections.observableArrayList(links);
        this.isLocked = new SimpleBooleanProperty(false);
        this.installationData = instData;

        installationPath = Paths.get("apps/" + title).toAbsolutePath();
        isDownloaded = new SimpleBooleanProperty(installationPath.toFile().exists());
        canBeUpdated = new SimpleBooleanProperty();
        canBeUpdated.bind(isDownloaded.and(this.installedVersion.isNotEqualTo(this.installationData.newestVersion)));
    }

    private Application() {
        this("", "", "", new ArrayList<>(), new ArrayList<>(), new InstallationData(null, "", new String[0]));
        this.isLocked.setValue(true);
    }

    public static final Application NULL_APP = new Application();
    private static ListProperty<Application> list = new SimpleListProperty<>();

    public static ListProperty<Application> getAppsList() {
        return list;
    }

    public static void setAppsList(List<Application> apps) {
        ObservableList<Application> newList = FXCollections.observableArrayList(apps);
        list.setValue(newList);
    }
}
