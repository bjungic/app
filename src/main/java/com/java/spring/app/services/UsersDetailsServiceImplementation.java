package com.java.spring.app.services;

import com.java.spring.app.model.User;
import com.java.spring.app.model.UserDetailsImplementation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsServiceImplementation implements UserDetailsService {

    private final UserService userService;

    public UsersDetailsServiceImplementation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImplementation: " + s);
        User user = userService.getUser(s);
        if (user == null) throw new UsernameNotFoundException(s);
        return new UserDetailsImplementation(user);
    }
}
