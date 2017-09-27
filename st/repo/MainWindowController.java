package st.repo;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {

    @FXML private ListView<Application> appList;
    @FXML private SplitPane splitPane;
    @FXML private AppInfoController appInfoPanelController;
    @FXML private Label statusText;
    @FXML public ProgressBar progressBar;
    @FXML private VBox root;

    private Stage stage;

    private ListProperty<Application> appListProperty = new SimpleListProperty<>();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        updateRepository();
        setDividerPosition();
        bindAppList();
        appInfoPanelController.setWindowController(this);
    }

    private void setDividerPosition() {
        ChangeListener chl = (object, oldValue, newValue) -> {
            splitPane.setDividerPositions(0.2);
        };
        splitPane.widthProperty().addListener(chl);
        splitPane.heightProperty().addListener(chl);
    }

    private void bindAppList() {
        appListProperty.bind(Application.getAppsList());
        appList.setCellFactory(list -> new ListCell<Application>(){
            @Override
            public void updateItem(Application app, boolean empty) {
                super.updateItem(app, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(app.title.getValue());
                }
            }
        });
        appList.setItems(appListProperty);

        appList.getSelectionModel().selectedItemProperty().addListener((ob, oldV, newV) -> {
            Application app = ob.getValue();
            appInfoPanelController.setApp(app);
            appInfoPanelController.reloadAppView();
        });
    }

    @FXML
    private void close() {
        stage.close();
        System.exit(0);
    }

    @FXML
    private void showAbout() {

    }

    @FXML
    private void updateRepository() {
        setDisable(true);
        try {
            FXMLLoader loader = new FXMLLoader(ReloadRepositoryController.class.getResource("reloadRepository.fxml"));
            BorderPane pane = loader.load();
            ReloadRepositoryController contr = loader.getController();
            Scene scene = new Scene(pane);
            Stage stg = Start.showDialog(scene, "Wczytywanie repozytorium...");
            contr.reloadRepository(stg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDisable(false);
    }

    public void setDisable(boolean disable) {
        root.setDisable(disable);
    }
}
