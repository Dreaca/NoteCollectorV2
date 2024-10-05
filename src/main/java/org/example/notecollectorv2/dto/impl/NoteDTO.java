package org.example.notecollectorv2.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.notecollectorv2.dto.NoteStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO implements NoteStatus {
    private String noteId;
    private String noteTitle;
    private String noteDesc;
    private String noteCreatedDate;
    private String priorityLevel;
    private String userId;
}
