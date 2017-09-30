package st.repo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class UpdateInfoController implements DefaultController {

    @FXML private Label appName;
    @FXML private Button yesButton;
    @FXML private Button noButton;
    @FXML private ProgressBar progressBar;

    private Stage stage;
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        System.out.println("init");
    }

    @FXML
    void installUpdate(ActionEvent event) {

    }

    @FXML
    void runApplication(ActionEvent event) {

    }

}
