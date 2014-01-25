import org.junit.Test;

/**
 * Created by jcnoir on 25/01/14.
 */
public class KUsbTest {

    @Test
    public void test() {
        KenwoodUsb kenwoodUsb = new KenwoodUsb("/media/donnees/test_kusb/source", "/media/donnees/test_kusb/target");
        kenwoodUsb.copy();
    }

}
