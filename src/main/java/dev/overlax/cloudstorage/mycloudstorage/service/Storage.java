package dev.overlax.cloudstorage.mycloudstorage.service;

import java.io.InputStream;

public interface Storage {

    void createBucket() throws Exception;

    void createBucket(String bucket) throws Exception;

    void save(InputStream inputStream, String filename) throws Exception;

    void delete(String bucket, String filename) throws Exception;

}
