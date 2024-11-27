package org.example.notecollectorv2.controller;

import lombok.RequiredArgsConstructor;
import org.example.notecollectorv2.dto.impl.UserDTO;
import org.example.notecollectorv2.entity.Role;
import org.example.notecollectorv2.exceptions.DataPersistException;
import org.example.notecollectorv2.secure.JWTAuthResponse;
import org.example.notecollectorv2.secure.SignIn;
import org.example.notecollectorv2.service.AuthService;
import org.example.notecollectorv2.service.UserService;
import org.example.notecollectorv2.util.AppUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class AuthUserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    @PostMapping(value = "/signup")
    public ResponseEntity<JWTAuthResponse> saveUser(
            @RequestPart("userFirstName") String userFirstName,
            @RequestPart("userLastName") String userLastName,
            @RequestPart("email")String userEmail,
            @RequestPart("userPassword")String userPassword,
            @RequestPart("role") String role,
            @RequestPart("profilePicture") MultipartFile profilePicture
    ) {
        //profile picture convert to base64
        String base64ProPic = null;
        try {
            byte [] bytePic = profilePicture.getBytes();
            base64ProPic = AppUtil.convertProfilePictureToBase64(bytePic);

            //UserId generate
            String userId = AppUtil.generateUserID();

            var buildUserDTO = new UserDTO();

            buildUserDTO.setUserId(userId);
            buildUserDTO.setUserFirstName(userFirstName);
            buildUserDTO.setUserLastName(userLastName);
            buildUserDTO.setUserEmail(userEmail);
            buildUserDTO.setUserPassword(passwordEncoder.encode(userPassword));
            buildUserDTO.setRole(Role.valueOf(role));
            buildUserDTO.setProfilePicture(base64ProPic);



            return ResponseEntity.ok(authService.signUp(buildUserDTO));
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping(value="/signin",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody SignIn signIn){
        return ResponseEntity.ok(authService.signIn(signIn));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestBody String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
