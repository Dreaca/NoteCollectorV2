package org.example.notecollectorv2.dao;

import org.example.notecollectorv2.entity.impl.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteDao extends JpaRepository<NoteEntity, String> {
}
