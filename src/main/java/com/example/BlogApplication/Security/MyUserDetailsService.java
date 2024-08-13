package com.example.BlogApplication.Security;

import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsername(username);

        return user.map(MyUserDetails::new)
                .orElseThrow(() ->new UsernameNotFoundException(username + "There is not such"));
    }
}