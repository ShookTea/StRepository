package st.repo;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public final StringProperty title;
    public final BooleanProperty isDownloaded;
    public final BooleanProperty canBeUpdated;
    public final BooleanProperty isLocked;
    public final StringProperty installedVersion;
    public final StringProperty newestVersion;
    public final StringProperty description;
    public final ObservableList<Link> authors;
    public final ObservableList<Link> links;

    private Application(String title, boolean isDownloaded, boolean canBeUpdated, boolean isLocked, String installedVersion, String newestVersion, String description, List<Link> authors, List<Link> links) {
        this.title = new SimpleStringProperty(title);
        this.isDownloaded = new SimpleBooleanProperty(isDownloaded);
        this.canBeUpdated = new SimpleBooleanProperty(canBeUpdated);
        this.isLocked = new SimpleBooleanProperty(isLocked);
        this.installedVersion = new SimpleStringProperty(installedVersion);
        this.newestVersion = new SimpleStringProperty(newestVersion);
        this.description = new SimpleStringProperty(description);
        this.authors = FXCollections.observableArrayList(authors);
        this.links = FXCollections.observableArrayList(links);
    }

    public Application(String title, boolean isDownloaded, boolean canBeUpdated, String installedVersion, String newestVersion, String description, List<Link> authors, List<Link> links) {
        this(title, isDownloaded, canBeUpdated, false, installedVersion, newestVersion, description, authors, links);
    }

    public Application(String title, boolean isDownloaded, boolean canBeUpdated, String installedVersion, String description) {
        this(title, isDownloaded, canBeUpdated, false, installedVersion, installedVersion, description, new ArrayList<>(), new ArrayList<>());
    }

    public Application() {
        this("", false, false, "", "");
    }

    public static final Application NULL_APP = new Application("", false, false, true, "", "", "", new ArrayList<>(), new ArrayList<>());
    private static ListProperty<Application> list = new SimpleListProperty<>();

    public static ListProperty<Application> getAppsList() {
        return list;
    }

    public static void setAppsList(List<Application> apps) {
        ObservableList<Application> newList = FXCollections.observableArrayList(apps);
        list.setValue(newList);
    }
}
