package st.repo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;

import java.net.URL;

public class InstallationData {
    public final URL downloadUrl;
    public final URL onlineJsonFile;
    public final String[] installationCommands;
    public final StringProperty newestVersion;

    public InstallationData(URL downloadUrl, String version, String[] installationCommands, URL onlineJsonFile) {
        this.downloadUrl = downloadUrl;
        this.installationCommands = installationCommands;
        this.newestVersion = new ReadOnlyStringWrapper(version);
        this.onlineJsonFile = onlineJsonFile;
    }

    public static final InstallationData NULL = new InstallationData(null, "", new String[0], null);
}
