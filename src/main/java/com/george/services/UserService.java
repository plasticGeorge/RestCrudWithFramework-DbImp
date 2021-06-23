package com.george.services;

import com.george.dto.UserFull;
import com.george.dto.UserLongId;
import com.george.entities.User;
import com.george.mappers.UserMapper;
import com.george.repositories.UserRepository;
import com.george.support.InvalidIdentifierException;
import com.george.support.PrimaryKeyViolationException;
import com.george.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void createAllUsers(UserLongId[] usersLongId) throws PrimaryKeyViolationException, InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        for (UserLongId userLongId : usersLongId)
            createUser(userLongId);
    }

    public void createUser(UserLongId userLongId) throws PrimaryKeyViolationException, InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        Long id = userLongId.getId();
        if(id != null && userRepository.existsById(id))
            throw new PrimaryKeyViolationException("User", id);
        User user = userMapper.mapToEntity(userLongId);
        userRepository.save(user);
    }

    public Iterable<UserLongId> getAllUsers(){
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapper::mapToLongId)
                .collect(Collectors.toList());
    }

    public UserLongId getUserById(Long id) throws InvalidIdentifierException {
        if(!userRepository.existsById(id))
            throw new InvalidIdentifierException("User", id);
        return userMapper.mapToLongId(userRepository.findById(id).orElse(null));
    }

    public UserFull fullInfo(Long id) throws InvalidIdentifierException {
        if(!userRepository.existsById(id))
            throw new InvalidIdentifierException("User", id);
        return userMapper.mapToFull(userRepository.fullInformation(id).orElse(null));
    }

    public void updateAllUsers(UserLongId[] usersLongId, boolean override) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        if (override) {
            for (UserLongId userLongId : usersLongId)
                overrideUser(userLongId);
        }
        else {
            for (UserLongId userLongId : usersLongId)
                updateUser(userLongId);
        }
    }

    public void updateUser(UserLongId userLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        Long id = userLongId.getId();
        if(id == null || !userRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        User newUser = userRepository.findById(id).orElse(null);
        User update = userMapper.mapToEntity(userLongId);
        if (update.getName() != null)
            newUser.setName(update.getName());
        if (update.getProject() != null)
            newUser.setProject(update.getProject());
        userRepository.save(newUser);
    }

    public void overrideUser(UserLongId userLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        Long id = userLongId.getId();
        if(id == null || !userRepository.existsById(id))
            throw new InvalidIdentifierException("User", id);
        User update = userMapper.mapToEntity(userLongId);
        userRepository.save(update);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public void deleteUserById(Long id) throws InvalidIdentifierException {
        if(!userRepository.existsById(id))
            throw new InvalidIdentifierException("User", id);
        userRepository.deleteById(id);
    }
}
