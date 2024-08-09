package dev.overlax.cloudstorage.mycloudstorage;

import dev.overlax.cloudstorage.mycloudstorage.model.User;
import io.minio.MinioClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
public class MyCloudstorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCloudstorageApplication.class, args);
    }

    @Bean
    CommandLineRunner init(JdbcTemplate jdbcTemplate) {
        return args -> {
            List<User> users = jdbcTemplate.query("select * from users",
                    new BeanPropertyRowMapper<>(User.class));
            System.out.println(users);
        };
    }

    @Bean
    CommandLineRunner test(MinioClient minioClient) {
        return args -> {

        };
    }

}
