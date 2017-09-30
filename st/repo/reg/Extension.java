package st.repo.reg;

public class Extension {
    public final String extension;
    public final String displayName;
    public final String registerName;
    public final String action;

    public Extension(String extension, String displayName, String action, String appName) {
        this.extension = "." + extension;
        this.displayName = displayName;
        this.action = action;
        this.registerName = "strep_" + appName.replace(" ", "_") + "." + extension;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[extension='" + extension + "';displayName='" + displayName + "';registerName='" + registerName + "';onAction='" + action + "']";
    }
}
