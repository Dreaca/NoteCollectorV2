package org.example.notecollectorv2.service;

import org.example.notecollectorv2.dto.NoteStatus;
import org.example.notecollectorv2.dto.impl.NoteDTO;

import java.util.List;

public interface NoteService {
    void saveNote(NoteDTO notedto);
    List<NoteDTO> getAllNotes();
    NoteStatus getSelectedNote(String noteId);
    void deleteNote(String noteId);
    void updateSelectedNote(String noteId,NoteDTO noteDTO);
}
