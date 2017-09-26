package st.repo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
