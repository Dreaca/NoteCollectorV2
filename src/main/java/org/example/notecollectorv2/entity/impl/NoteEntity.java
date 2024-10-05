package org.example.notecollectorv2.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.notecollectorv2.entity.SuperEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="note")
public class NoteEntity implements SuperEntity{
    @Id
    private String noteId;
    private String noteTitle;
    private String noteDesc;
    private String noteCreatedDate;
    private String priorityLevel;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;
}
