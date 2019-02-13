package com.ibm.services.data.manager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.services.data.entity.User;
import com.ibm.services.data.repo.UserRepository;
import com.ibm.services.exception.UserNameAlreadyTakenException;


@Component
public class UserManager {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User createUserModel) throws UserNameAlreadyTakenException {
//        User existingUser  = userRepository.findByUsername(createUserModel.getLoginName());
//        if (existingUser != null) {
//            throw new UserNameAlreadyTakenException();
//        }

        User user =  userRepository.save(createUserModel);
        return user;
    }
    
    

    public User getUserById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }
    
    public User getUserByUsername(String emailAddress) {
    	return userRepository.findByUsername(emailAddress);
    }
    
    public List<User> getUsersByUserType(String type) {
    	return userRepository.findAllByUserType(type);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }
    
}
