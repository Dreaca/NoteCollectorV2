package org.example.notecollectorv2.controller;

import org.example.notecollectorv2.customStatusCodes.SelectedUserAndNoteErrorStatus;
import org.example.notecollectorv2.dto.UserStatus;
import org.example.notecollectorv2.dto.impl.UserDTO;
import org.example.notecollectorv2.exceptions.DataPersistException;
import org.example.notecollectorv2.exceptions.UserNotFoundException;
import org.example.notecollectorv2.service.UserService;
import org.example.notecollectorv2.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(
            @RequestPart("userFirstName") String userFirstName,
            @RequestPart("userLastName") String userLastName,
            @RequestPart("userEmail")String userEmail,
            @RequestPart("userPassword")String userPassword,
            @RequestPart("profilePicture") MultipartFile profilePicture
    ) {
        //profile picture convert to base64
        String base64ProPic = null;
        try {
            byte [] bytePic = profilePicture.getBytes();
            base64ProPic = AppUtil.convertProfilePictureToBase64(bytePic);

        //UserId generate
        String userId = AppUtil.generateUserID();
        // Todo: Build the object

        var buildUserDTO = new UserDTO();

        buildUserDTO.setUserId(userId);
        buildUserDTO.setUserFirstName(userFirstName);
        buildUserDTO.setUserLastName(userLastName);
        buildUserDTO.setUserEmail(userEmail);
        buildUserDTO.setUserPassword(userPassword);
        buildUserDTO.setProfilePicture(base64ProPic);

        userService.saveUser(buildUserDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value="/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserStatus getSelectedUser(@PathVariable("userId") String userId) {
        String regexForUserID = "^UID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(userId);

        if (!regexMatcher.matches()) {
            return new SelectedUserAndNoteErrorStatus(1,"USER ID NOT VALID");
        }
        return userService.getUser(userId);
    }
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        String regexForUserID = "^UID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(userId);
        try {
            if(!regexMatcher.matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUser(
            @PathVariable ("userId") String userId,
            @RequestPart ("userFirstName") String firstName,
            @RequestPart ("userLastName") String lastName,
            @RequestPart ("userEmail") String email,
            @RequestPart ("userPassword") String password,
            @RequestPart ("profilePicture") MultipartFile profilePic
    ){
        System.out.println(userId);
        // profilePic ----> Base64
        String base64ProPic = "";
        try {
            byte [] bytesProPic = profilePic.getBytes();
            base64ProPic = AppUtil.convertProfilePictureToBase64(bytesProPic);
        }catch (Exception e){
            e.printStackTrace();
        }
        //Build the Object
        UserDTO buildUserDTO = new UserDTO();
        buildUserDTO.setUserId(userId);
        buildUserDTO.setUserFirstName(firstName);
        buildUserDTO.setUserLastName(lastName);
        buildUserDTO.setUserEmail(email);
        buildUserDTO.setUserPassword(password);
        buildUserDTO.setProfilePicture(base64ProPic);
        userService.updateUser(userId,buildUserDTO);
    }

}
