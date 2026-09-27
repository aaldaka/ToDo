package com.ga.todoApp.security;

import com.ga.todoApp.model.User;
import com.ga.todoApp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Security;


@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
//    Security Packaged/classes
//    our classes     -> implementations     -> spring security interface
//    myuserdetailsservice        UserDetailsService
//    MyUserDetail                UserDetails
//    SecurityConfiguration

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(email);
        return new MyUserDetails(user);
    }
}
