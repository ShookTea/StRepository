package st.repo;

public class RunInfo {

    private final String appName;
    private final String command;

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
}
