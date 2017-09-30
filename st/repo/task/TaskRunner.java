package st.repo.task;

import javafx.concurrent.Task;
import st.repo.Application;
import st.repo.controller.DefaultController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskRunner {
    public static void runTask(boolean disposeAtEnd, Runnable runAtEnd, DefaultController contr, Application app, Task... tasks) {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.addAll(Arrays.asList(tasks));
        runTask(disposeAtEnd, runAtEnd, taskList, contr, app);
    }

    public static void runTask(DefaultController contr, Application app, Task... tasks) {
        runTask(false, () -> {}, contr, app, tasks);
    }

    private static void runTask(boolean disposeAtEnd, Runnable runAtEnd, List<Task> taskList, DefaultController windowController, Application app) {
        Task currentTask = taskList.remove(0);
        currentTask.setOnRunning(e -> {
            windowController.getRoot().setDisable(true);
            windowController.getProgressBar().progressProperty().bind(currentTask.progressProperty());
        });
        currentTask.setOnSucceeded(e -> {
            windowController.getProgressBar().progressProperty().unbind();
            windowController.getProgressBar().setProgress(0.0);
            app.updateStatus();
            if (taskList.size() > 0) {
                runTask(disposeAtEnd, runAtEnd, taskList, windowController, app);
            }
            else {
                windowController.getRoot().setDisable(false);
                if (disposeAtEnd) {
                    windowController.dispose();
                    runAtEnd.run();
                }
            }
        });
        currentTask.setOnFailed(e -> {
            System.exit(1);
        });
        new Thread(currentTask).start();
    }
}
