package dev.overlax.cloudstorage.mycloudstorage.repository;

import dev.overlax.cloudstorage.mycloudstorage.model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {

}
