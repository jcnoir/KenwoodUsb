import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class for CLI
 */
public class Main {

    @Parameter
    private List<String> parameters = new ArrayList<String>();

    @Parameter(names = "-source", description = "Source folder to read music files", required = true)
    private String sourceFolder;

    @Parameter(names = "-target", description = "Target folder to write music files", required = true)
    private String targetFolder;

    public static void main(String[] args) {

        KenwoodUsb kenwoodUsb;
        Main jct = new Main();
        JCommander cmd = new JCommander();

        try {
            cmd.addObject(jct);
            cmd.parse(args);
            kenwoodUsb = new KenwoodUsb(jct.sourceFolder, jct.targetFolder);
            kenwoodUsb.copy();
        } catch (ParameterException ex) {
            System.out.println(ex.getMessage());
            cmd.usage();
        }

    }
}
