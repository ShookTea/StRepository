package st.repo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReloadRepositoryController {

    @FXML
    private ProgressIndicator progress;

    public void reloadRepository(Stage stageToDispose) {
        DoubleProperty currentProgress = new SimpleDoubleProperty(-1.0);
        progress.progressProperty().bind(currentProgress);

        ArrayList<URL> urls = new ArrayList<>();
        try {
            URL repositoryList = new URL("https://raw.githubusercontent.com/ShookTea/StRepository/master/repository.txt");
            String[] repo = loadFile(repositoryList).split("\n");
            for (String line : repo) {
                line = line.trim();
                if (!line.startsWith("#")) urls.add(new URL(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
            stageToDispose.close();
        }

        double stepPerProgress = 1.0 / ((double)urls.size());

        List<Application> applications = urls.stream().map(url -> {
            Application app = null;
            try {
                app = loadApplicationFromURL(url);
                currentProgress.set(currentProgress.get() + stepPerProgress);
                return app;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
        currentProgress.set(1.0);

        applications.removeIf(app -> app == null);
        Application.setAppsList(applications);
        stageToDispose.close();
    }

    private Application loadApplicationFromURL(URL url) throws IOException {
        String jsonFile = "";
        jsonFile = loadFile(url);
        JSONParser parser = new JSONParser();
        JSONObject root;
        try {
            root = (JSONObject)parser.parse(jsonFile);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        String name = (String)root.get("name");
        String newestVersion = (String)root.get("version");
        String descriptionLink = (String)root.getOrDefault("description", null);
        String description = descriptionLink == null ? "" : loadFile(new URL(descriptionLink));

        Object authors = root.getOrDefault("authors", null);
        List<Link> authorsList = parseLinkTable(authors);

        return new Application(name, false, false, newestVersion, description);
    }

    private String loadFile(URL url) throws IOException {
        InputStream is = url.openStream();
        Scanner sc = new Scanner(is);
        String file = "";
        while (sc.hasNextLine()) {
            file = file + sc.nextLine() + "\n";
        }
        sc.close();
        is.close();
        return file;
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
}
