package dev.overlax.cloudstorage.mycloudstorage.service;

import dev.overlax.cloudstorage.mycloudstorage.configuration.MinioProperty;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MinioStorage implements Storage {

    private final MinioClient client;
    private final MinioProperty property;


    @Override
    public void save(InputStream inputStream, String filename) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        client.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(property.getBucket())
                .object(filename)
                .build());
    }

    @Override
    public void createBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!checkBucketExists(property.getBucket())) {
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(property.getBucket())
                    .build());
        }
    }

    @Override
    public void createBucket(String bucket) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!checkBucketExists(bucket)) {
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build());
        }
    }

    @Override
    public void delete(String username, String filename) throws Exception {
        StringBuilder path = new StringBuilder();
        if (username != null && filename != null) {
            path.append(username).append("/").append(filename);
        }

        client.removeObject(RemoveObjectArgs.builder()
                .bucket(property.getBucket())
                .object(path.toString())
                .build());
    }

    private boolean checkBucketExists(String bucket) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        return client.bucketExists(BucketExistsArgs.builder()
                .bucket(bucket)
                .build());
    }

    public Iterable<Result<Item>> getObjectsInfo(String username) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        createBucket();
        return client.listObjects(
                ListObjectsArgs.builder()
                        .bucket(property.getBucket())
                        .prefix(username + "/")
                        .build()
        );
    }
}
