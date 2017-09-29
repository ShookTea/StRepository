package st.repo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import st.repo.controller.MainWindowController;
import st.repo.reg.Registry;

import java.io.File;
import java.net.URISyntaxException;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Start.class.getResource("mainWindow.fxml"));
        VBox mainWindow = loader.load();
        MainWindowController controller = loader.getController();

        controller.setStage(primaryStage);
        Scene scene = new Scene(mainWindow);

        primaryStage.setScene(scene);
        primaryStage.setTitle("StRepository");
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(new String[0]);
    }

    public static Stage showDialog(Scene sc, String title) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setScene(sc);
        dialog.setTitle(title);
        dialog.show();
        return dialog;
    }

    public static final Registry REGISTRY = Registry.getInstance();
    public static final File getJarFile() {
        try {
            return new File(Start.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }
}
