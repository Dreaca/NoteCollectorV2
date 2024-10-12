package org.example.notecollectorv2.service.impl;

import jakarta.transaction.Transactional;
import org.example.notecollectorv2.customStatusCodes.SelectedUserAndNoteErrorStatus;
import org.example.notecollectorv2.dao.UserDao;
import org.example.notecollectorv2.dto.UserStatus;
import org.example.notecollectorv2.dto.impl.UserDTO;
import org.example.notecollectorv2.entity.impl.UserEntity;
import org.example.notecollectorv2.exceptions.DataPersistException;
import org.example.notecollectorv2.exceptions.UserNotFoundException;
import org.example.notecollectorv2.service.UserService;
import org.example.notecollectorv2.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveUser(UserDTO user) {
        UserEntity saved = userDao.save(mapping.toUserEntity(user));
        if (saved == null) {
            throw new DataPersistException("User Not Saved");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapping.toUserDTOList(userDao.findAll());
    }

    @Override
    public UserStatus getUser(String userId) {
        if (userDao.existsById(userId)) {
            UserEntity byId = userDao.getReferenceById(userId);
            return mapping.toUserDTO(byId);
        }else {
            return new SelectedUserAndNoteErrorStatus(2,"User with id "+ userId +" Not Found");
        }

    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> existedUser = userDao.findById(userId);
        if(!existedUser.isPresent()){
            throw new UserNotFoundException("User with id " + userId + " not found");
        }else {
            userDao.deleteById(userId);
        }
    }
    @Override
    public void updateUser(String userId, UserDTO userDTO) {
        Optional<UserEntity> tmpUser = userDao.findById(userId);
        if(tmpUser.isPresent()) {
            tmpUser.get().setUserFirstName(userDTO.getUserFirstName());
            tmpUser.get().setUserLastName(userDTO.getUserLastName());
            tmpUser.get().setUserEmail(userDTO.getUserEmail());
            tmpUser.get().setUserPassword(userDTO.getUserPassword());
            tmpUser.get().setProfilePicture(userDTO.getProfilePicture());
        }
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return userName -> userDao.findByEmail(userName)
                .orElseThrow(()->new UserNotFoundException("User with id"+userName+" is not available"));
    }
}
