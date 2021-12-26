package netology.cloud.service;

import netology.cloud.exceptions.DiplomaException;
import netology.cloud.model.CurrentUser;
import netology.cloud.model.FileList;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CloudService {
    public static final String ROOT_PATH = "files/";
    public final CurrentUser currentUser;


    public CloudService(CurrentUser currentUser) {
        this.currentUser = currentUser;
        checkRootPath();
    }


    public void uploadFile(String fileName, byte[] fileContext) {
        var path = getRootPath().concat(fileName);
        var file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Something went wrong");
            }
        }
        try (var fileWriter = new FileOutputStream(path)) {
            fileWriter.write(fileContext);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }

    private String getRootPath() {
        var root = ROOT_PATH
                .concat(String.valueOf(currentUser.getId())).
                concat("/");
        var dir = new File(root);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("создал папку");
            } else {
                System.out.println("ошибка создания папки");
            }
        }
        return root;
    }


    public void deleteFile(String fileName) {
        var path = getRootPath().concat(fileName);
        var file = new File(path);
        checkFile(file);
        if (!file.delete()) {
            throw new RuntimeException("Error deleting file");
        }
    }

    public byte[] getFile(String fileName) {
        var path = getRootPath().concat(fileName);
        var file = new File(path);
        checkFile(file);
        try (var fileReader = new FileInputStream(file)) {
            byte[] fileContext = new byte[(int) file.length()];
            fileReader.read(fileContext);
            return fileContext;
        } catch (Exception e) {
            throw new RuntimeException("Error upload file");
        }
    }

    private void checkFile(File file) {
        if (!file.exists()) {
            throw new DiplomaException("Error input data");
        }
    }

    public void editFile(String fileName, String name) {
        var root = getRootPath();
        var path = root.concat(fileName);
        var file = new File(path);
        var newFile = new File(root.concat(name));
        checkFile(file);
        if (!file.renameTo(newFile)) {
            throw new RuntimeException("Error edit file");
        }
    }

    public List<FileList> getFiles(int limit) {
        var root = getRootPath();
        var dir = new File(root);
        return Arrays.stream(dir.list())
                .limit(limit)
                .map((i) -> new FileList(i, (int) (new File(root.concat(i))).length()))
                .toList();
    }

    private void checkRootPath() {
        var dir = new File(ROOT_PATH);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("создал папку");
            } else {
                System.out.println("ошибка создания папки");
            }
        }
    }

}
