package dev.overlax.cloudstorage.mycloudstorage.service;

import dev.overlax.cloudstorage.mycloudstorage.configuration.MinioProperty;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileService {

    private final MinioClient client;
    private final MinioProperty property;

    public String upload(MultipartFile file) throws FileUploadException {
        if (file == null) {
            System.out.println("ASDASDASDASDasdfasdasSDSD");
        }
        try {
            createBucket();
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
            log.info("Uploading file {} to bucket {}", filename, property.getBucket());
            save(inputStream, filename);
        } catch (Exception e) {
            log.error("Cannot upload file {}", filename, e);
            throw new FileUploadException("Can not save the file " + filename + " " + e.getMessage());
        }

        return filename;
    }

    private void save(InputStream inputStream, String filename) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        client.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(property.getBucket())
                .object(filename)
                .build());
    }

    private void createBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean found = client.bucketExists(BucketExistsArgs.builder()
                .bucket(property.getBucket())
                .build());
        if (!found) {
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(property.getBucket())
                    .build());
        }
    }
}
