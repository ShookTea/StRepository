package st.repo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.net.URL;

public class InstallationData {
    public final URL downloadUrl;
    public final String[] installationCommands;
    public final StringProperty newestVersion;

    public InstallationData(URL downloadUrl, String version, String[] installationCommands) {
        this.downloadUrl = downloadUrl;
        this.installationCommands = installationCommands;
        this.newestVersion = new ReadOnlyStringWrapper(version);
    }

    public void startDownloadTo(File folder) {
        installationPhase.set(0.0);
        if (!folder.mkdirs()) {
            System.err.println("Error: cannot create folder " + folder.getPath());
            System.exit(0);
        }
    }

    public static final DoubleProperty installationPhase = new SimpleDoubleProperty(0.0);
}
