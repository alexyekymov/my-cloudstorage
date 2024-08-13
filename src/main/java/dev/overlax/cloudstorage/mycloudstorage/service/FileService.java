package dev.overlax.cloudstorage.mycloudstorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileService {

    public final MinioStorage minioStorage;

    public String upload(MultipartFile file) throws FileUploadException {
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

        String filename = file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            minioStorage.save(inputStream, filename);
        } catch (Exception e) {
            log.error("Cannot upload file {}", filename, e);
            throw new FileUploadException("Can not save the file " + filename + " " + e.getMessage());
        }

        return filename;
    }
}
