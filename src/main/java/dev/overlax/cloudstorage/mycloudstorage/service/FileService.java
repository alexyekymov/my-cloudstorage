package dev.overlax.cloudstorage.mycloudstorage.service;

import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileService {

    public final MinioStorage minioStorage;

    public String upload(MultipartFile file, String username) throws FileUploadException {
        try {
            minioStorage.createBucket();
        } catch (Exception e) {
            log.error("Failed to create bucket", e);
            throw new FileUploadException("Bucket creation exception" + e.getMessage());
        }

        if (file.isEmpty() || file.getOriginalFilename() == null) {
            log.error("File is empty or does not have name");
            throw new FileUploadException("File must have name");
        }

        String filename = username + "/" + file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            minioStorage.save(inputStream, filename);
        } catch (Exception e) {
            log.error("Cannot upload file {}", filename, e);
            throw new FileUploadException("Can not save the file " + filename + " " + e.getMessage());
        }

        return filename;
    }

    public List<String> getFilesList(String username) {

        Iterable<Result<Item>> objectsInfo = minioStorage.getObjectsInfo(username);

        List<String> fileNames = new ArrayList<>();

        for (Result<Item> objectInfo : objectsInfo) {
            try {
                var item = objectInfo.get();
                fileNames.add(item.objectName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return fileNames;
    }
}
