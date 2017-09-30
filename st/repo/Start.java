package st.repo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import st.jargs.Flag;
import st.jargs.FlagBuilder;
import st.jargs.Parser;
import st.jargs.WrongArgumentException;
import st.repo.controller.DefaultController;
import st.repo.controller.MainWindowController;
import st.repo.controller.UpdateInfoController;
import st.repo.reg.Registry;
import st.repo.task.ReloadRepositoryTask;

import java.io.File;
import java.net.URISyntaxException;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (runInfo.isRunDefault()) runRepository(primaryStage);
        else {
            st.repo.Application.setAppsList(ReloadRepositoryTask.reload());
            st.repo.Application app = st.repo.Application.getAppByName(runInfo.appName);
            if (app.canBeUpdated.get()) {
                showUpdateInfo(primaryStage, app);
            }
            else {
                app.runApplication(runInfo.command);
                System.exit(0);
            }
        }
    }

    private void runRepository(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Start.class.getResource("mainWindow.fxml"));
        VBox mainWindow = loader.load();
        MainWindowController controller = loader.getController();
        Scene scene = new Scene(mainWindow);

        show(controller, scene, "StRepository", primaryStage);
    }

    private void showUpdateInfo(Stage primaryStage, st.repo.Application app) throws Exception {
        FXMLLoader loader = new FXMLLoader(Start.class.getResource("updateInfo.fxml"));
        VBox mainWindow = loader.load();
        UpdateInfoController controller = loader.getController();
        controller.setApplication(app);
        controller.setRunInfo(runInfo);
        Scene scene = new Scene(mainWindow);

        show(controller, scene, "Informacja o aktualizacji - StRepository", primaryStage);
    }

    private void show(DefaultController dc, Scene scene, String title, Stage primaryStage) {
        dc.setStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        runInfo = checkArgs(args);
        launch(new String[0]);
    }

    public static RunInfo runInfo = new RunInfo();
    public static final Registry REGISTRY = Registry.getInstance();

    public static Stage showDialog(Scene sc, String title) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setScene(sc);
        dialog.setTitle(title);
        dialog.show();
        return dialog;
    }

    public static final File getJarFile() {
        try {
            return new File(Start.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    private static RunInfo checkArgs(String[] args) {
        if (args.length == 0) return new RunInfo();
        try {
            Flag appNameFlag = FlagBuilder.createFlag().setVariableRequired().setLongFlag("application").setShortFlag('a').build();
            Flag runCommandFlag = FlagBuilder.createFlag().setVariableRequired().setLongFlag("runcommand").setShortFlag('r').build();
            Parser argParser = Parser.createParser(appNameFlag, runCommandFlag);
            argParser.parse(args);
            String name = appNameFlag.isUsed() ? appNameFlag.getValue() : null;
            String run = runCommandFlag.isUsed() ? runCommandFlag.getValue() : null;
            return new RunInfo(name, run);
        } catch (WrongArgumentException e) {
            e.printStackTrace();
            return new RunInfo();
        }
    }
}
