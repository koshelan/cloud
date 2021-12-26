package netology.cloud.service;

import netology.cloud.model.CurrentUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CloudServiceTest {
    private static final String ROOT = "files/";
    private static final String ID = "test";
    private static final String TEST_FILE_NAME = "test1.txt";
    private static final CurrentUser currentUser = new CurrentUser(ID);
    private static final CloudService cloudService = new CloudService(currentUser);

    @BeforeAll
    static void prepare() {
        var file = new File("src/test/resources/" .concat(TEST_FILE_NAME));
        try (var fileReader = new FileInputStream(file)) {
            byte[] fileContext = new byte[(int) file.length()];
            fileReader.read(fileContext);
            cloudService.uploadFile(file.getName(), fileContext);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterAll
    static void clearAll() {
        var path = ROOT.concat(ID).concat("/");
        var testDir = new File(path);
        for (String fileName : testDir.list()) {
            new File(path.concat(fileName)).delete();
        }
        testDir.delete();
    }

    @Test
    void uploadFile() {
        var fileName = "test.txt";
        var file = new File("src/test/resources/" .concat(fileName));
        try (var fileReader = new FileInputStream(file)) {
            byte[] fileContext = new byte[(int) file.length()];
            fileReader.read(fileContext);
            cloudService.uploadFile(file.getName(), fileContext);
            var testDir = new File(ROOT.concat(ID).concat("/"));
            assertTrue(Arrays.stream(testDir.list()).filter((it) -> fileName.equals(it)).findFirst().isPresent());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void deleteFile() {
        var testDir = new File(ROOT.concat(ID).concat("/"));
        assertTrue(Arrays.stream(testDir.list()).filter((it) -> TEST_FILE_NAME.equals(it)).findFirst().isPresent());
        cloudService.deleteFile(TEST_FILE_NAME);
        assertTrue(!Arrays.stream(testDir.list()).filter((it) -> TEST_FILE_NAME.equals(it)).findFirst().isPresent());
        prepare();
    }

    @Test
    void getFile() {
        var file = new File("src/test/resources/" .concat(TEST_FILE_NAME));
        try (var fileReader = new FileInputStream(file)) {
            byte[] res = new byte[(int) file.length()];
            fileReader.read(res);
            assertArrayEquals(res, cloudService.getFile(TEST_FILE_NAME));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void editFile() {
        var oldFileName = TEST_FILE_NAME;
        var newFileName = "testfile.txt";
        cloudService.editFile(oldFileName, newFileName);
        assertTrue((new File(ROOT.concat(ID).concat("/").concat(newFileName))).exists());
        assertTrue(!(new File(ROOT.concat(ID).concat("/").concat(oldFileName))).exists());
        cloudService.editFile(newFileName, oldFileName);
        assertTrue((new File(ROOT.concat(ID).concat("/").concat(oldFileName))).exists());
    }

    @Test
    void getFiles() {
        var res = cloudService.getFiles(1);
        assertTrue(res.size() == 1);
        res = cloudService.getFiles(5);
        assertTrue(res.stream().map((it) -> it.filename()).filter(it -> TEST_FILE_NAME.equals(it)).findFirst().isPresent());
    }
}