import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by jcnoir on 25/01/14.
 */
public class KUsbTest {

    public static final int MUSIC_FILE_NUMBER = 1000;
    @Rule
    public TemporaryFolder tempSourceFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder tempTargetFolder = new TemporaryFolder();

    @Test
    public void testFirstCopy() throws IOException {
        createTempFiles();
        KenwoodUsb kenwoodUsb = new KenwoodUsb(tempSourceFolder.getRoot(), tempTargetFolder.getRoot());
        kenwoodUsb.copy();

        assertEquals("Wrong number of sub-directories in the target directory", 4, tempTargetFolder.getRoot().list().length);
        assertEquals("Wrong number of files in the target directory", MUSIC_FILE_NUMBER, FileUtils.listFiles(tempTargetFolder.getRoot(), null, true).size());
    }

    @Test
    public void testUpdate() throws IOException {
        createTempFiles();
        KenwoodUsb kenwoodUsb = new KenwoodUsb(tempSourceFolder.getRoot(), tempTargetFolder.getRoot());
        kenwoodUsb.copy();
        File file = tempSourceFolder.newFile("test_music_file_NEW.mp3");
        file.createNewFile();
        kenwoodUsb.copy();

        assertEquals("Wrong number of sub-directories in the target directory", 4, tempTargetFolder.getRoot().list().length);
        assertEquals("Wrong number of files in the target directory", MUSIC_FILE_NUMBER + 1, FileUtils.listFiles(tempTargetFolder.getRoot(), null, true).size());
    }

    private void createTempFiles() throws IOException {
        for (int i = 0; i < MUSIC_FILE_NUMBER; i++) {
            File file = tempSourceFolder.newFile("test_music_file_" + i + ".mp3");
            file.createNewFile();
            File imageFile = tempSourceFolder.newFile("test_image_file_" + i + ".jpeg");
            imageFile.createNewFile();
        }
    }
}
