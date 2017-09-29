package st.repo.reg;

import st.repo.Application;

public class Extension {
    public final String extension;
    public final String displayName;
    public final String registerName;
    public final String action;

    public Extension(String extension, String displayName, String action, Application app) {
        this.extension = extension;
        this.displayName = displayName;
        this.action = action;
        this.registerName = "strep." + app.title.get() + "." + extension;
    }
}
