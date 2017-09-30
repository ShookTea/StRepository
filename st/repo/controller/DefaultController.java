package st.repo.controller;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface DefaultController {
    public void setStage(Stage stage);
    public VBox getRoot();
    public ProgressBar getProgressBar();
}
