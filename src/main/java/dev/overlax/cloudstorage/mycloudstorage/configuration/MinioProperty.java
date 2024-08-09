package dev.overlax.cloudstorage.mycloudstorage.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperty {
    private String access;
    private String secret;
    private String bucket;
    private String endpoint;
}
