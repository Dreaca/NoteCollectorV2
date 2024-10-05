package org.example.notecollectorv2.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String generateID(){
        return "NOTEID"+ UUID.randomUUID();
    }
    public static String generateUserID(){
        return "UID"+ UUID.randomUUID();
    }
    public static String convertProfilePictureToBase64(byte[] profilePicture){
       return Base64.getEncoder().encodeToString(profilePicture);
    }
    public static String generateNoteId(){
        return "NOTEID"+ UUID.randomUUID();
    }
}
