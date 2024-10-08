package org.example.notecollectorv2.controller;

import org.example.notecollectorv2.customStatusCodes.SelectedUserAndNoteErrorStatus;
import org.example.notecollectorv2.dto.NoteStatus;
import org.example.notecollectorv2.dto.impl.NoteDTO;
import org.example.notecollectorv2.exceptions.DataPersistException;
import org.example.notecollectorv2.exceptions.NoteNotFoundException;
import org.example.notecollectorv2.service.NoteService;
import org.example.notecollectorv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody NoteDTO noteDTO) {

        try {
            noteService.saveNote(noteDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{noteID}",produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteStatus getSelectedNote(@PathVariable ("noteID") String noteId){
        String regexForUserID = "^NOTID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(noteId);
        if (!regexMatcher.matches()) {
            return new SelectedUserAndNoteErrorStatus(1,"Note ID is not valid");
        }
        return noteService.getSelectedNote(noteId);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO> getAllNotes(){
        return noteService.getAllNotes();
    }

    @DeleteMapping(value = "/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable ("noteId") String noteId){
        String regexForUserID = "^NOTEID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(noteId);
        try {
            if (!regexMatcher.matches()) {

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.deleteNote(noteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{noteId}")
    public ResponseEntity<Void> updateNote(@PathVariable ("noteId") String noteId,
                                           @RequestBody NoteDTO updatedNoteDTO) {
        //validations
        String regexForUserID = "^NOTEID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(noteId);
        try {
            if (!regexMatcher.matches() || updatedNoteDTO == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.updateSelectedNote(noteId, updatedNoteDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
