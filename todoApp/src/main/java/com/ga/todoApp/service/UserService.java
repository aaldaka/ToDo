package com.ga.todoApp.service;

import com.ga.todoApp.exception.InformationExistException;
import com.ga.todoApp.model.User;
import com.ga.todoApp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User userObj){
        System.out.println("Calling service createUser");
        if(!userRepository.existsByEmailAddress(userObj.getEmailAddress())){
            userObj.setPassword(passwordEncoder.encode(userObj.getPassword()));
            return userRepository.save(userObj);
        }else{
            throw new InformationExistException("A user with the entered email address already exists.");
        }
    }

}
