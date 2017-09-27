package st.repo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
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
        new Thread(() -> {
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
            installationPhase.set(-1.0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            installationPhase.set(0.0);
        }).start();
    }

    private void download(URL url, File folder) throws IOException {
        URLConnection urlc = url.openConnection();
        String urlFile = url.getFile();
        String fileName = urlFile.substring(urlFile.lastIndexOf('/')+1, urlFile.length());
        long bytes = urlc.getContentLength();
        long kibiBytes = bytes / 1024 + 1;
        double progressPerKiB = 1.0 / ((double)kibiBytes);

        InputStream stream = urlc.getInputStream();
        ReadableByteChannel rbc = Channels.newChannel(stream);
        File downloadFile = new File(folder, fileName);
        downloadFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(downloadFile);
        FileChannel channel = fos.getChannel();

        long actualPosition = 0;
        long downloaded = -1;
        double actualProgress = 0.0;
        while (downloaded != 0) {
            downloaded = channel.transferFrom(rbc, actualPosition, 1024);
            actualPosition += downloaded;
            double progressChange = (downloaded * progressPerKiB) / 1024.0;
            actualProgress += progressChange;
            installationPhase.set(actualProgress);
        }

        channel.close();
        fos.close();
        rbc.close();
        stream.close();
    }

    public static final DoubleProperty installationPhase = new SimpleDoubleProperty(0.0);
}
