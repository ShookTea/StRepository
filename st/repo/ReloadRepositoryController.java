package st.repo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

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
            InputStream is = repositoryList.openStream();
            Scanner sc = new Scanner(is);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.startsWith("#")) urls.add(new URL(line));
            }
            sc.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            stageToDispose.close();
        }

        double stepPerProgress = 1.0 / ((double)urls.size());

        List<Application> applications = urls.stream().map(url -> {
            Application app = loadApplicationFromURL(url);
            currentProgress.set(currentProgress.get() + stepPerProgress);
            return app;
        }).collect(Collectors.toList());
        currentProgress.set(1.0);

        Application.setAppsList(applications);
        stageToDispose.close();
    }

    private Application loadApplicationFromURL(URL url) {
        return new Application(url.getPath(), false, false, "1.0", "opis");
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
}
