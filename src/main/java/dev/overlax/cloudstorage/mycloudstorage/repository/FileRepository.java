package dev.overlax.cloudstorage.mycloudstorage.repository;

import dev.overlax.cloudstorage.mycloudstorage.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

}
