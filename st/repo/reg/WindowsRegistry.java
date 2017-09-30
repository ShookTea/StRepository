package st.repo.reg;

import java.io.File;
import java.io.IOException;
import java.util.Map;
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
                "REG ADD " + keyNameVal + " /ve /d \"" + ext.displayName + "\" /f",
                "REG ADD " + keyNameVal + "\\shell\\open\\command /ve /d \"" + ext.action + "\" /f"
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
            err.close();
            code = code.trim();
            return code.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static File getJavawPath() {
        if (javawPath == null) {
            Map<String, String> envVar = System.getenv();
            System.getenv().entrySet().stream().forEach(WindowsRegistry::checkEnvKey);
            String[] parts = envVar.getOrDefault(pathEnvKey, "").split(";");
            String path = findJavapath(parts);
            File mainPath = new File(path);
            javawPath = new File(mainPath, "javaw.exe");
        }
        return javawPath;
    }

    private static String findJavapath(String[] parts) {
        for (String part : parts) {
            if (part.toUpperCase().contains("JAVAPATH")) return part.trim();
        }
        return null;
    }

    private static void checkEnvKey(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        if (key.toUpperCase().equals("PATH")) {
            pathEnvKey = key;
        }
        if (pathEnvKey == null) {
            pathEnvKey = "C;\\ProgramData\\Oracle\\Java\\javapath";
        }
    }

    private static File javawPath = null;
    private static String pathEnvKey = null;
}
