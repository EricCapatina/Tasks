import org.junit.Test;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;
public class MyApplicationTest
{
    MyApplication myApplication = new MyApplication();
    Path currentRelativePath = Paths.get("");
    String relativePath = currentRelativePath.toAbsolutePath().toString();

    @Test
    public void createUserTest()
    {
        File file = new File(relativePath + "/Chat.db");
        assertTrue( "File Chat.db should exist", file.exists());
    }

    @Test
    public void createDatabaseText() throws IllegalAccessException
    {
        try {
            myApplication.createDataBase();
        } catch (Exception e) {
            fail("Should not throw any exceptions.");
        }
    }
}
