package st.repo.reg;

public abstract class Registry {
    public boolean createExtension(Extension ext) {
        if (createExtensionForAdmin(ext)) return true;
        return createExtensionForUser(ext);
    }

    protected abstract boolean createExtensionForUser(Extension ext);
    protected abstract boolean createExtensionForAdmin(Extension ext);

    public boolean removeExtension(Extension ext) {
        if (removeExtensionForAdmin(ext)) return true;
        return removeExtensionForUser(ext);
    }

    protected abstract boolean removeExtensionForUser(Extension ext);
    protected abstract boolean removeExtensionForAdmin(Extension ext);

    public static Registry getInstance() {
        return null;
    }
}
