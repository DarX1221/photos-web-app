package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(AppUser appUser) {
        userRepository.save(appUser);
    }


}
