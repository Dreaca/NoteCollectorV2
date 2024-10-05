package org.example.notecollectorv2.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.notecollectorv2.dto.UserStatus;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements UserStatus {
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private String profilePicture;
    private List<NoteDTO> notes;
}
