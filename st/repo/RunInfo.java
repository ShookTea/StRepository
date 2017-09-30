package st.repo;

public class RunInfo {

    public final String appName;
    public final String command;

    public RunInfo(String appName, String command) {
        this.appName = appName;
        this.command = command;
    }

    public RunInfo(String appName) {
        this(appName, null);
    }

    public RunInfo() {
        this(null);
    }

    public boolean isRunDefault() {
        return appName == null;
    }

    public boolean isRunApp() {
        return appName != null;
    }
}
