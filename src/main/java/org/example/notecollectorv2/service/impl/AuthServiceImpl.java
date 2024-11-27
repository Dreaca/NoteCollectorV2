package org.example.notecollectorv2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.notecollectorv2.dao.UserDao;
import org.example.notecollectorv2.dto.impl.UserDTO;
import org.example.notecollectorv2.entity.impl.UserEntity;
import org.example.notecollectorv2.secure.JWTAuthResponse;
import org.example.notecollectorv2.secure.SignIn;
import org.example.notecollectorv2.service.AuthService;
import org.example.notecollectorv2.service.JWTService;
import org.example.notecollectorv2.util.Mapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        UserEntity user = userDao.findByEmail(signIn.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String generateToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generateToken).build();
    }

    @Override
    public JWTAuthResponse signUp(UserDTO user) {
        //Save user
        UserEntity save = userDao.save(mapping.toUserEntity(user));
        //Generate Token and return
        String generateToken = jwtService.generateToken(save);
        return JWTAuthResponse.builder().token(generateToken).build();

    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        String username = jwtService.extractUsername(accessToken);
        UserEntity user = userDao.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String refreshToken = jwtService.refreshToken(user);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}
