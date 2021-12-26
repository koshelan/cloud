package netology.cloud.controller;

import netology.cloud.model.FileList;
import netology.cloud.model.NewFileName;
import netology.cloud.service.CloudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CloudController {
    private final CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    @PostMapping(value = "/file")
    public void uploadFile(
            @RequestParam("filename") String fileName,
            @RequestPart("file") byte[] file) {
        cloudService.uploadFile(fileName, file);
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestParam("filename") String fileName) {
        cloudService.deleteFile(fileName);
    }

    @GetMapping(value = "/file")
    public byte[] getFile(
            @RequestParam("filename") String fileName) {
        return cloudService.getFile(fileName);
    }

    @PutMapping("/file")
    public void editFile(
            @RequestParam("filename") String fileName,
            @RequestBody NewFileName name) {
        cloudService.editFile(fileName, name.fileName());
    }

    @GetMapping("/list")
    public List<FileList> getFiles(@RequestParam int limit) {
        return cloudService.getFiles(limit);
    }


}
