package st.repo.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import st.repo.Application;
import st.repo.task.ReloadRepositoryTask;

import java.util.List;

public class ReloadRepositoryController {

    @FXML
    private ProgressIndicator progress;

    public void reloadRepository(Stage stageToDispose) {
        Task<List<Application>> task = new ReloadRepositoryTask();
        task.setOnSucceeded(e -> {
            stageToDispose.close();
            Application.setAppsList(task.getValue());
        });
        task.setOnFailed(e -> {
            stageToDispose.close();
        });
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
}
