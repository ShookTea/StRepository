package st.repo;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ReloadRepositoryController {

    @FXML
    private ProgressIndicator progress;

    public void reloadRepository(Stage stageToDispose) {
        try {
            progress.setProgress(-1.0);
            URL repositoryList = new URL("https://raw.githubusercontent.com/ShookTea/StRepository/master/repository.txt");
            InputStream is = repositoryList.openStream();
            Scanner sc = new Scanner(is);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }
            sc.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stageToDispose.close();
    }

}
