package st.repo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;

public class MainWindowController {

    @FXML
    private ListView<?> appList;

    @FXML
    private Label appTitle;

    @FXML
    private Label appVersion;

    @FXML
    private FlowPane appAuthorFlowPane;

    @FXML
    private Label appDescription;

    @FXML
    private FlowPane appLinksFlowPane;

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void downloadApp(ActionEvent event) {

    }

    @FXML
    void removeApp(ActionEvent event) {

    }

    @FXML
    void showAbout(ActionEvent event) {

    }

    @FXML
    void showChangelog(ActionEvent event) {

    }

    @FXML
    void updateApp(ActionEvent event) {

    }

    @FXML
    void updateRepository(ActionEvent event) {

    }

}
