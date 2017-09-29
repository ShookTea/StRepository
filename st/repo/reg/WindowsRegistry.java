package st.repo.reg;

import java.io.IOException;
import java.util.Scanner;

public class WindowsRegistry extends Registry {
    @Override
    protected boolean updateExtensionForUser(Extension ext) {
        return createExtensionForUser(ext);
    }

    @Override
    protected boolean updateExtensionForAdmin(Extension ext) {
        return createExtensionForAdmin(ext);
    }

    @Override
    protected boolean createExtensionForUser(Extension ext) {
        return create("HKCU\\Software\\Classes", ext);
    }

    @Override
    protected boolean createExtensionForAdmin(Extension ext) {
        return create("HKLM\\SOFTWARE\\Classes", ext);
    }

    @Override
    protected boolean removeExtensionForUser(Extension ext) {
        return remove("HKCU\\Software\\Classes", ext);
    }

    @Override
    protected boolean removeExtensionForAdmin(Extension ext) {
        return remove("HKLM\\SOFTWARE\\Classes", ext);
    }

    private boolean create(String key, Extension ext) {
        String keyNameExt = key + "\\" + ext.extension;
        String keyNameVal = key + "\\" + ext.registerName;

        return runCommands(
                "REG ADD " + keyNameExt + " /ve /d " + ext.registerName + " /f",
                "REG ADD " + keyNameVal + " /ve /d " + ext.displayName + "/ f",
                "REG ADD " + keyNameVal + "\\shell\\open\\command /ve /d " + ext.action + " /f"
        );
    }

    private boolean remove(String key, Extension ext) {
        String keyNameExt = key + "\\" + ext.extension;
        String keyNameVal = key + "\\" + ext.registerName;

        return runCommands(
                "REG DELETE " + keyNameExt + " /f",
                "REG DELETE " + keyNameVal + " /f"
        );
    }

    private boolean runCommands(String... commands) {
        for (String command : commands) {
            if (!runCommand(command)) return false;
        }
        return true;
    }

    private boolean runCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            Scanner err = new Scanner(process.getErrorStream());
            String code = "";
            while (err.hasNextLine()) {
                code += err.nextLine();
            }
            code = code.trim();
            return code.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
