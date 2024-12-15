package com.aa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aa.dto.User;
import com.aa.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUser(Long id, User updates) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (updates.getName() != null) {
            user.setName(updates.getName());
        }
        if (updates.getEmail() != null) {
            user.setEmail(updates.getEmail());
        }
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}