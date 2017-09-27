package st.repo.task;

import javafx.concurrent.Task;
import st.repo.Application;

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
    private final URL jsonUrl;
    private final String name;
    private File downloadFile = null;
    private final String[] commands;
    private long size = 0;

    public DownloadTask(Application app) {
        this.folder = app.installationPath.toFile();
        this.downloadUrl = app.installationData.downloadUrl;
        this.jsonUrl = app.installationData.onlineJsonFile;
        this.name = app.title.get();
        this.commands = app.installationData.installationCommands;
    }

    @Override
    protected Object call() throws Exception {
        updateProgress(-1, -1);
        if (!folder.mkdirs()) {
            System.err.println("Error: cannot create folder " + folder.getPath());
            System.exit(0);
        }
        downloadJSON();
        init(downloadUrl, folder);
        updateTitle("Pobieranie...");
        updateMessage("Pobieranie pliku " + downloadFile.getName() + " z adresu " + downloadUrl.toString());
        updateProgress(0, size);
        download();
        install();
        return null;
    }

    public void init(URL url, File folder) throws IOException {
        URLConnection urlc = url.openConnection();
        String urlFile = url.getFile();
        String fileName = urlFile.substring(urlFile.lastIndexOf('/')+1, urlFile.length());
        size = urlc.getContentLength();
        downloadFile = new File(folder, fileName);
    }

    private void downloadJSON() throws IOException {
        File jsonFile = new File(folder.getParent(), name.replace(" ", "_") + ".strep");
        initChannels(jsonUrl, jsonFile);
        channel.transferFrom(rbc, 0, Long.MAX_VALUE);
        closeChannels();
    }

    private void download() throws IOException {
        initChannels(downloadUrl, downloadFile);
        long actualPosition = 0;
        long downloaded = -1;
        while (downloaded != 0) {
            downloaded = channel.transferFrom(rbc, actualPosition, 1024);
            actualPosition += downloaded;
            updateProgress(actualPosition, size);
        }
        closeChannels();
    }

    private void install() {
        for (String command : commands) {

        }
    }

    private void initChannels(URL src, File dest) throws IOException {
        stream = src.openStream();
        rbc = Channels.newChannel(stream);
        dest.createNewFile();
        fos = new FileOutputStream(dest);
        channel = fos.getChannel();
    }

    private void closeChannels() throws IOException {
        channel.close();
        fos.close();
        rbc.close();
        stream.close();
    }

    private InputStream stream;
    private ReadableByteChannel rbc;
    private FileOutputStream fos;
    private FileChannel channel;
}
