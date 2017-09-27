package st.repo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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
        try {
            download(downloadUrl, folder);
        } catch (IOException e) {
            e.printStackTrace();
            if (!folder.delete()) folder.deleteOnExit();
            System.exit(0);
        }
    }

    private void download(URL url, File folder) throws IOException {
        URLConnection urlc = url.openConnection();
        String urlFile = url.getFile();
        String fileName = urlFile.substring(urlFile.lastIndexOf('/')+1, urlFile.length());
        System.out.println("FILE NAME: " + fileName);
        double bytes = urlc.getContentLength();
        //InputStream stream = urlc.getInputStream();
        //ReadableByteChannel rbc = Channels.newChannel(stream);

    }

    public static final DoubleProperty installationPhase = new SimpleDoubleProperty(0.0);
}
