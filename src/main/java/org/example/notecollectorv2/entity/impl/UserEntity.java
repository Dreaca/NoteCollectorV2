package org.example.notecollectorv2.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.notecollectorv2.entity.SuperEntity;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class UserEntity implements SuperEntity {
    @Id
    private String userId;
    private String userFirstName;
    private String userLastName;
    @Column(unique=true)
    private String userEmail;
    private String userPassword;
    @Column(columnDefinition = "LONGTEXT")
    private String profilePicture;
    @OneToMany(mappedBy = "user")
    private List<NoteEntity> notes;
}
