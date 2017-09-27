package st.repo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

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


    public Task createDownloadTask(File folder) {
        return new DownloadTask(folder, downloadUrl);
    }
}
