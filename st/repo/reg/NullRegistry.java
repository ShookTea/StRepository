package st.repo.reg;

public class NullRegistry extends Registry {
    @Override
    protected boolean createExtensionForUser(Extension ext) {
        return false;
    }

    @Override
    protected boolean createExtensionForAdmin(Extension ext) {
        return false;
    }

    @Override
    protected boolean removeExtensionForUser(Extension ext) {
        return false;
    }

    @Override
    protected boolean removeExtensionForAdmin(Extension ext) {
        return false;
    }

    @Override
    protected boolean updateExtensionForUser(Extension ext) {
        return false;
    }

    @Override
    protected boolean updateExtensionForAdmin(Extension ext) {
        return false;
    }
}
