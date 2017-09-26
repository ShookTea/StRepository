package st.repo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML private ListView<?> appList;
    @FXML private Label appTitle;
    @FXML private Label appVersion;
    @FXML private FlowPane appAuthorFlowPane;
    @FXML private Label appDescription;
    @FXML private FlowPane appLinksFlowPane;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void close(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void downloadApp(ActionEvent event) {

    }

    @FXML
    private void removeApp(ActionEvent event) {

    }

    @FXML
    private void showAbout(ActionEvent event) {

    }

    @FXML
    private void showChangelog(ActionEvent event) {

    }

    @FXML
    private void updateApp(ActionEvent event) {

    }

    @FXML
    private void updateRepository(ActionEvent event) {

    }

    @FXML
    private void runApp(ActionEvent actionEvent) {

    }
}
