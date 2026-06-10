package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.entity.User;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        return user.getPassword().equals(password);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}