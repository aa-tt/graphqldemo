package com.aa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aa.dto.User;
import com.aa.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUser(Long id, User updates) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (updates.getName() != null) {
                user.setName(updates.getName());
            }
            if (updates.getEmail() != null) {
                user.setEmail(updates.getEmail());
            }
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }
}