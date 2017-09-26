package st.repo;

import javafx.beans.binding.When;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;

import java.util.stream.Collectors;

public class AppInfoController {

    @FXML private Label appTitle;
    @FXML private ToolBar appToolbar;
    @FXML private Button runAppButton;
    @FXML private Button downloadAppButton;
    @FXML private Button updateAppButton;
    @FXML private Button removeAppButton;
    @FXML private Button showChangelogButton;
    @FXML private Label appVersion;
    @FXML private FlowPane appAuthorFlowPane;
    @FXML private Label appDescription;
    @FXML private FlowPane appLinksFlowPane;

    private ObjectProperty<Application> currentApp = new SimpleObjectProperty<>(Application.NULL_APP);

    public void setApp(Application app) {
        if (app == null) {
            currentApp.set(Application.NULL_APP);
        }
        else {
            currentApp.set(app);
        }
    }

    @FXML
    private void initialize() {
        bindToolbarButtonsWidth();
        reloadAppView();
    }

    private void bindToolbarButtonsWidth() {
        ObservableList<Node> buttons = appToolbar.getItems();
        for (Node n : buttons) {
            Button button = (Button)n;
            button.prefWidthProperty().bind(appToolbar.widthProperty().divide(appToolbar.getItems().size()).subtract(10));
        }
    }

    public void reloadAppView() {
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

        createLinksList(appAuthorFlowPane, currentApp.get().authors);
        createLinksList(appLinksFlowPane, currentApp.get().links);
    }

    private void createLinksList(FlowPane fp, ObservableList<Link> links) {
        fp.getChildren().clear();
        fp.getChildren().addAll(links.stream().map(link -> {
            Hyperlink hyperlink = new Hyperlink(link.title);
            hyperlink.setOnAction(actionEvent -> openWebPage(link.title, link.link));
            return hyperlink;
        }).collect(Collectors.toList()));
    }

    private void openWebPage(String title, String address) {
        WebView wv = new WebView();
        wv.getEngine().load(address);
        Scene scene = new Scene(new Group(wv));
        wv.prefWidthProperty().bind(scene.widthProperty());
        wv.prefHeightProperty().bind(scene.heightProperty());
        Start.showDialog(scene, title);
    }

    @FXML
    private void downloadApp() {

    }

    @FXML
    private void removeApp() {

    }

    @FXML
    private void runApp() {

    }

    @FXML
    private void showChangelog() {

    }

    @FXML
    private void updateApp() {

    }
}
