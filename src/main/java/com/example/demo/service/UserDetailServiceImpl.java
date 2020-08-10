package com.example.demo.service;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;


    @Autowired
    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /*
        todo
        Optional<AppUser> user = userRepository.findByUsername(s);
        user.orElseThrow(() -> new UsernameNotFoundException(s + " not found."));
        return user.map(UserDetailServiceImpl::new).get();
        /**/

        return userService.findByUsername(s);
    }




}
