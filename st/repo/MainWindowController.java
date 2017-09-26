package st.repo;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML private ListView<?> appList;
    @FXML private Label appTitle;
    @FXML private Label appVersion;
    @FXML private FlowPane appAuthorFlowPane;
    @FXML private Label appDescription;
    @FXML private FlowPane appLinksFlowPane;
    @FXML private ToolBar appToolbar;
    @FXML private SplitPane splitPane;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        setDividerPosition();
        bindToolbarButtonsWidth();
    }

    private void setDividerPosition() {
        ChangeListener chl = (object, oldValue, newValue) -> {
            splitPane.setDividerPositions(0.2);
        };
        splitPane.widthProperty().addListener(chl);
        splitPane.heightProperty().addListener(chl);
    }

    private void bindToolbarButtonsWidth() {
        ObservableList<Node> buttons = appToolbar.getItems();
        for (Node n : buttons) {
            Button button = (Button)n;
            button.prefWidthProperty().bind(appToolbar.widthProperty().divide(appToolbar.getItems().size()).subtract(10));
        }
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
