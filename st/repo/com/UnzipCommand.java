package st.repo.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipCommand extends Command {
    UnzipCommand() {
        super("unzip");
    }

    @Override
    public void run(File dir, String[] args) {
        if (args.length == 0) return;
        byte[] buffer = new byte[1024];
        try {
            File zipFile = new File(dir, args[0]);
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(dir, fileName);
                newFile.getParentFile().mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
