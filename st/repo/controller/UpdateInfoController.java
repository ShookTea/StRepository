package st.repo.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import st.repo.Application;
import st.repo.RunInfo;
import st.repo.task.TaskRunner;

public class UpdateInfoController implements DefaultController {

    @FXML private Label appName;
    @FXML private Button yesButton;
    @FXML private Button noButton;
    @FXML private ProgressBar progressBar;
    @FXML private VBox root;

    private Stage stage;
    private Application app = null;
    private RunInfo runInfo;
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setApplication(Application app) {
        this.app = app;
        appName.textProperty().bind(new SimpleStringProperty("Program ").concat(app.title).concat(" ma nową wersję."));
    }

    public void setRunInfo(RunInfo runInfo) {
        this.runInfo = runInfo;
    }

    @FXML
    private void initialize() {
        HBox hbox = (HBox)yesButton.getParent();
        yesButton.prefWidthProperty().bind(hbox.widthProperty().divide(2).subtract(0));
        noButton.prefWidthProperty().bind(hbox.widthProperty().divide(2).subtract(0));
        progressBar.prefWidthProperty().bind(hbox.widthProperty());
    }

    @FXML
    private void installUpdate(ActionEvent event) {
        if (app == null) return;
        Task remove = app.createRemoveTask();
        Task install = app.createDownloadTask();
        TaskRunner.runTask(true, this, app, remove, install);
    }

    @FXML
    private void runApplication(ActionEvent event) {
        if (app == null) return;
        app.runApplication(runInfo.command);
        dispose();
    }

    @Override
    public VBox getRoot() {
        return root;
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void dispose() {
        stage.close();
    }
}
