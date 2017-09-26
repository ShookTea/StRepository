package st.repo;

import javafx.beans.binding.When;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML private ListView<Application> appList;
    @FXML private Label appTitle;
    @FXML private Label appVersion;
    @FXML private FlowPane appAuthorFlowPane;
    @FXML private Label appDescription;
    @FXML private FlowPane appLinksFlowPane;
    @FXML private ToolBar appToolbar;
    @FXML private SplitPane splitPane;
    @FXML private Button runAppButton;
    @FXML private Button downloadAppButton;
    @FXML private Button updateAppButton;
    @FXML private Button removeAppButton;
    @FXML private Button showChangelogButton;

    private Stage stage;
    private ObjectProperty<Application> currentApp = new SimpleObjectProperty<>(Application.NULL_APP);
    private ListProperty<Application> appListProperty = new SimpleListProperty<>();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        setDividerPosition();
        bindToolbarButtonsWidth();
        bindAppView();
        bindAppList();


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
            if (app == null) {
                currentApp.set(Application.NULL_APP);
            }
            else {
                currentApp.set(app);
            }
            bindAppView();
        });
    }

    private void bindAppView() {
        appTitle.textProperty().bind(currentApp.get().title);
        appVersion.textProperty().bind(
                new When(currentApp.get().canBeUpdated.and(currentApp.get().isDownloaded))
                .then(currentApp.get().installedVersion.concat(" (najnowsza wersja: ").concat(currentApp.get().newestVersion).concat(")"))
                .otherwise(
                        new When(currentApp.get().isDownloaded)
                        .then(currentApp.get().installedVersion)
                        .otherwise(currentApp.get().newestVersion)));
        appDescription.textProperty().bind(currentApp.get().description);

        runAppButton.disableProperty().bind(currentApp.get().isLocked.or(currentApp.get().isDownloaded.not()));
        downloadAppButton.disableProperty().bind(currentApp.get().isLocked.or(currentApp.get().isDownloaded));
        updateAppButton.disableProperty().bind(currentApp.get().isLocked.or(currentApp.get().canBeUpdated.not()).or(currentApp.get().isDownloaded.not()));
        removeAppButton.disableProperty().bind(currentApp.get().isLocked.or(currentApp.get().isDownloaded.not()));
        showChangelogButton.disableProperty().bind(currentApp.get().isLocked);

        runAppButton.defaultButtonProperty().bind(currentApp.get().isDownloaded.and(currentApp.get().canBeUpdated.not()));
        downloadAppButton.defaultButtonProperty().bind(currentApp.get().isDownloaded.not());
        updateAppButton.defaultButtonProperty().bind(currentApp.get().isDownloaded.and(currentApp.get().canBeUpdated));
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
