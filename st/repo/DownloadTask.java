package st.repo;

import javafx.concurrent.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class DownloadTask extends Task {
    private final File folder;
    private final URL downloadUrl;
    private File downloadFile = null;
    private long size = 0;

    public DownloadTask(File folder, URL downloadUrl) {
        this.folder = folder;
        this.downloadUrl = downloadUrl;
    }

    @Override
    protected Object call() throws Exception {
        updateProgress(-1, -1);
        init(downloadUrl, folder);
        updateTitle("Pobieranie...");
        updateMessage("Pobieranie pliku " + downloadFile.getName() + " z adresu " + downloadUrl.toString());
        updateProgress(0, size);
        download();
        return null;
    }

    private void init(URL url, File folder) throws IOException {
        if (!folder.mkdirs()) {
            System.err.println("Error: cannot create folder " + folder.getPath());
            System.exit(0);
        }
        URLConnection urlc = url.openConnection();
        String urlFile = url.getFile();
        String fileName = urlFile.substring(urlFile.lastIndexOf('/')+1, urlFile.length());
        size = urlc.getContentLength();
        downloadFile = new File(folder, fileName);
    }

    private void download() throws IOException {
        InputStream stream = downloadUrl.openStream();
        ReadableByteChannel rbc = Channels.newChannel(stream);
        downloadFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(downloadFile);
        FileChannel channel = fos.getChannel();

        long actualPosition = 0;
        long downloaded = -1;
        while (downloaded != 0) {
            downloaded = channel.transferFrom(rbc, actualPosition, 1024);
            actualPosition += downloaded;
            updateProgress(actualPosition, size);
        }

        channel.close();
        fos.close();
        rbc.close();
        stream.close();
    }
}
