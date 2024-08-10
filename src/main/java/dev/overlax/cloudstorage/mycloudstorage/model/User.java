package dev.overlax.cloudstorage.mycloudstorage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;

    private String email;

    private String name;

    @OneToMany(mappedBy = "owner")
    private List<File> files;
}
