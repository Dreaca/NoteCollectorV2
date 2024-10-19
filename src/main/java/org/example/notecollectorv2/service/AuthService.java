package org.example.notecollectorv2.service;

import org.example.notecollectorv2.dto.impl.UserDTO;
import org.example.notecollectorv2.secure.JWTAuthResponse;
import org.example.notecollectorv2.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDTO user);
    JWTAuthResponse refreshToken(String accessToken);
}
