package dev.overlax.cloudstorage.mycloudstorage.configuration;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MinioClient minioClient(MinioProperty minioProperty) {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(minioProperty.getEndpoint())
                        .credentials(minioProperty.getAccess(), minioProperty.getSecret())
                        .build();

        return minioClient;
    }
}
