import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;

/**
 * http://www.kenwood.com/cs/ce/audiofile/index.php?model=DNX7230DAB&lang=english
 */
public class KenwoodUsb {

    private static final int MAX_FOLDER_LAYER = 8;
    private static final int MAX_DEVICE_FOLDERS = 255;
    private static final int MAX_FOLDER_FILES = 255;
    private static final int MAX_DEVICE_FILES = 65025;
    private static final int MAX_PLAYLIST_FILES = 7000;
    private static final String FOLDER_PREFIX = "KUSB";
    public static final Logger LOG = LoggerFactory.getLogger(KenwoodUsb.class);
    private static final String[] EXT = {"mp3", "wma", "m4a", "aac", "wav", "MP3", "WMA", "M4A", "AAC", "WAV"};
    public static final int MIN_DISK_SPACE = 1024 * 1024 * 10;
    private File sourceFolder;
    private File targetFolder;
    private File firstAvaliableFolder;

    public KenwoodUsb(File sourceFolder, File targetFolder) {
        this.sourceFolder = sourceFolder;
        this.targetFolder = targetFolder;
    }

    public KenwoodUsb(String sourceFolder, String targetFolder) {
        this(new File(sourceFolder), new File(targetFolder));
    }

    public void copy() {
        LOG.info("Copy music from : " + sourceFolder + " to : " + targetFolder + " ...");
        copy(sourceFolder, targetFolder);
    }

    private void copy(File sourceFolder, File targetFolder) {

        if (!sourceFolder.isDirectory()) {
            LOG.warn("The source directory does not exist : " + sourceFolder);
        }

        this.firstAvaliableFolder = getAvailableFolder(targetFolder);

        if (firstAvaliableFolder == null) {
            LOG.warn("The target directory is full or does not exist : " + targetFolder);
            return;
        }

        Collection<File> sourceFiles = FileUtils.listFiles(sourceFolder, EXT, true);
        Collection<File> targetFiles = FileUtils.listFiles(targetFolder, EXT, true);
        int fileCounter = 1;

        for (File file : sourceFiles) {

            if (targetFolder.getFreeSpace() < MIN_DISK_SPACE) {
                LOG.warn("No more disk space");
                return;
            }
            String targetFileName = Math.abs(file.getAbsolutePath().hashCode()) + "." + FilenameUtils.getExtension(file.getName());
            if (!isDuplicate(targetFileName, file.length(), targetFiles)) {
                try {
                    File targetSubDir = getAvailableFolder(targetFolder);
                    if (targetSubDir != null) {
                        LOG.debug(fileCounter + "/" + sourceFiles.size() + " : Copy : " + file.getName() + " --> " + targetSubDir.getName() + "/" + targetFileName);
                        FileUtils.copyFile(file, new File(targetSubDir, targetFileName));
                    }
                } catch (Exception ex) {
                    LOG.debug(fileCounter + "/" + sourceFiles.size() + " : Copy failure for : " + file + " : " + ex);
                }
            } else {
                LOG.debug(fileCounter + "/" + sourceFiles.size() + " : Copy already exists in target folder : " + file.getName());
            }
            fileCounter++;
        }
    }

    private boolean isFolderAvailable(File folder) {
        return folder.isDirectory() && folder.list().length < MAX_FOLDER_FILES;
    }

    private File getAvailableFolder(File rootFolder) {
        for (int i = 0; i < MAX_DEVICE_FOLDERS; i++) {
            File folder = new File(rootFolder, FOLDER_PREFIX + String.format("%03d", i));
            if (!folder.isDirectory()) {
                folder.mkdir();
            }
            if (isFolderAvailable(folder)) {
                return folder;
            }
        }
        return null;
    }

    private boolean isDuplicate(String fileName, long fileLength, Collection<File> files) {
        for (File existingFile : files) {
            if (existingFile.getName().equals(fileName) && existingFile.length() == fileLength) {
                return true;
            }
        }
        return false;
    }
}