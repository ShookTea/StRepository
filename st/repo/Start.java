package st.repo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("mainWindow.fxml"));
        VBox mainWindow = loader.load();
        MainWindowController controller = loader.getController();

        controller.setStage(primaryStage);
        Scene scene = new Scene(mainWindow);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("StRepository");
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
            System.exit(0);
        });
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage showDialog(Scene sc, String title) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setScene(sc);
        dialog.setTitle(title);
        dialog.show();
        return dialog;
    }
}
