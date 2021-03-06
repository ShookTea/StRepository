package st.repo.task;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import javafx.concurrent.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import st.repo.Application;
import st.repo.InstallationData;
import st.repo.Link;
import st.repo.Start;
import st.repo.reg.Extension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class ReloadRepositoryTask extends Task<List<Application>> {
    @Override
    protected List<Application> call() throws Exception {
        updateTitle("Wczytywanie repozytorium...");
        updateMessage("Wczytywanie repozytorium...");
        updateProgress(-1, -1);

        try {
            return getOnlineApplications();
        } catch (IOException ex) {
            return getOfflineApplications();
        }
    }

    private List<Application> getOnlineApplications() throws IOException {
        URL repositoryList = new URL("https://raw.githubusercontent.com/ShookTea/StRepository/master/repository.txt");
        List<URL> urls = loadRepositoryURLs(repositoryList);
        int appsCount = urls.size();
        int currentApp = 0;
        updateProgress(currentApp, appsCount);

        List<Application> applications = new ArrayList<>();
        for (URL url : urls) {
            applications.add(loadApplicationFromURL(url));
            currentApp++;
            updateProgress(currentApp, appsCount);
        }

        applications.removeIf(Objects::isNull);
        return applications;
    }

    private List<Application> getOfflineApplications() throws IOException {
        File installationPath = Start.getJarFile().isDirectory() ?
                Paths.get("apps/").toFile() :
                new File(Start.getJarFile().getParentFile(), "apps/");
        File[] strepFiles = installationPath.listFiles(file ->
           file.isFile() && file.getName().toLowerCase().endsWith(".strep")
        );
        List<Application> applications = new ArrayList<>();
        for (File strepFile : strepFiles) {
            String appName = strepFile.getName();
            appName = appName.substring(0, appName.length() - 6);
            File appFolder = new File(strepFile.getParent(), appName);
            if (appFolder.exists() && appFolder.isDirectory()) {
                applications.add(loadApplicationFromFile(strepFile));
            }
        }
        applications.removeIf(Objects::isNull);
        return applications;
    }

    private List<URL> loadRepositoryURLs(URL repoList) throws IOException {
        List<URL> ret = new ArrayList<>();
        String[] repo = loadFile(repoList).split("\n");
        for (String line : repo) {
            line = line.trim();
            if (!line.startsWith("#")) {
                ret.add(new URL(line));
            }
        }
        return ret;
    }

    private String loadFile(URL url) throws IOException {
        try {
            InputStream is = url.openStream();
            Scanner sc = new Scanner(is);
            StringBuilder file = new StringBuilder();
            while (sc.hasNextLine()) {
                file.append(sc.nextLine()).append("\n");
            }
            sc.close();
            is.close();
            return file.toString();
        } catch (IOException ex) {
            return "";
        }
    }

    private Application loadApplicationFromURL(URL jsonUrl) throws IOException {
        String jsonFile = loadFile(jsonUrl);
        return parseJSON(jsonFile, jsonUrl);
    }

    private Application loadApplicationFromFile(File strepFile) throws IOException {
        Scanner sc = new Scanner(strepFile);
        String json = "";
        while (sc.hasNextLine()) {
            json += sc.nextLine() + "\n";
        }
        try {
            return parseJSON(json, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Application parseJSON(String jsonFile, URL jsonUrl) throws IOException {
        JSONParser parser = new JSONParser();
        JSONObject root;
        try {
            root = (JSONObject)parser.parse(jsonFile);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        String name = (String)root.get("name");
        String descriptionLink = (String)root.getOrDefault("description", null);
        String description = descriptionLink == null ? "" : loadFile(new URL(descriptionLink));
        String runningCommand = (String)root.getOrDefault("runCommand", "");

        Object authors = root.getOrDefault("authors", null);
        List<Link> authorsList = parseLinkTable(authors);
        Object links = root.getOrDefault("links", null);
        List<Link> linksList = parseLinkTable(links);

        String newestVersion = (String)root.get("version");
        URL downloadPath = new URL(root.get("download").toString());
        String[] installationCommands = parseCommands(root.getOrDefault("installation", "").toString());
        InstallationData instData = new InstallationData(downloadPath, newestVersion, installationCommands, jsonUrl);

        Object extensions = root.getOrDefault("associatedFiles", null);
        Extension[] extList = parseExtensions(extensions, name);

        Application app = new Application(name, description, authorsList, linksList, instData, runningCommand, extList);
        return app;
    }

    private Extension[] parseExtensions(Object extensions, String appName) {
        List<Extension> extensionList = new ArrayList<>();
        if (extensions instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject)extensions;
            extensionList.add(parseExtension(jsonObject, appName));
        }
        else if (extensions instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)extensions;
            for (Object element : jsonArray) {
                JSONObject jsonObject = (JSONObject)element;
                extensionList.add(parseExtension(jsonObject, appName));
            }
        }
        return extensionList.toArray(new Extension[0]);
    }

    private Extension parseExtension(JSONObject jsonObject, String appName) {
        String extension = (String)jsonObject.get("extension");
        String name = (String)jsonObject.getOrDefault("name", "Plik programu " + appName);
        String action = (String)jsonObject.get("runCommand");
        action = action.replace("\"", "^");
        action = "-a \"" + appName + "\" -r \"" + action + "\"";
        return new Extension(extension, name, action, appName);
    }

    private List<Link> parseLinkTable(Object ob) {
        List<Link> list = new ArrayList<>();
        if (ob instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject)ob;
            list.add(parseLinkObject(jsonObject));
        }
        else if (ob instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)ob;
            for (Object element : jsonArray) {
                JSONObject objElement = (JSONObject)element;
                list.add(parseLinkObject(objElement));
            }
        }
        return list;
    }

    private Link parseLinkObject(JSONObject ob) {
        String name = (String)ob.get("name");
        String link = (String)ob.get("link");
        return new Link(name, link);
    }

    private String[] parseCommands(String command) {
        String[] installationCommands = command.split(";");
        Object[] installCommObj = Arrays.stream(installationCommands).map(String::trim).filter(s -> !s.isEmpty()).toArray();
        installationCommands = new String[installCommObj.length];
        for (int i = 0; i < installCommObj.length; i++) {
            installationCommands[i] = (String)installCommObj[i];
        }
        return installationCommands;
    }

    public static List<Application> reload() throws Exception {
        ReloadRepositoryTask t = new ReloadRepositoryTask();
        return t.call();
    }
}
