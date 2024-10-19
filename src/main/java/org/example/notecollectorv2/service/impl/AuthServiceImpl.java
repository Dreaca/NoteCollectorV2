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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final Mapping mapping;
    private final JWTService jwtService;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        return null;
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
        return null;
    }
}
