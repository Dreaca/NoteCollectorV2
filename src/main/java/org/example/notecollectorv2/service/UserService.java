package org.example.notecollectorv2.service;

import org.example.notecollectorv2.dto.UserStatus;
import org.example.notecollectorv2.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO user);
    List<UserDTO> getAllUsers();
    UserStatus getUser(String userId);
    void deleteUser(String userId);
    void updateUser(String userId, UserDTO user);
    UserDetailsService getUserDetailsService();
}
