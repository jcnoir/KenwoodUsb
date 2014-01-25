import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcnoir on 25/01/14.
 */
public class Main {

    @Parameter
    private List<String> parameters = new ArrayList<String>();

    @Parameter(names = "-source", description = "Source folder", required = true)
    private String sourceFolder;

    @Parameter(names = "-target", description = "Target folder", required = true)
    private String targetFolder;

    public static void main(String[] args) {
        KenwoodUsb kenwoodUsb;
        Main jct = new Main();
        new JCommander(jct, args);

        kenwoodUsb = new KenwoodUsb(jct.sourceFolder, jct.targetFolder);
        kenwoodUsb.copy();


    }
}
