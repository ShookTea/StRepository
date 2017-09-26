package st.repo;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class ReloadRepositoryController {

    @FXML
    private ProgressIndicator progress;

    public void reloadRepository(Stage stageToDispose) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stageToDispose.close();
    }

}
