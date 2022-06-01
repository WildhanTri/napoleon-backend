package com.example.napoleon.service;

import java.util.List;
import java.util.Optional;

import com.example.napoleon.model.User;
import com.example.napoleon.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
	@Autowired
	private UserRepository userRepository;

    public User findByUuid(String token) {
        Optional<User> user = userRepository.findByUuid(token);
        return user.isPresent() ? user.get() : null;
	}

    public User findByToken(String token) {
        Optional<User> user = userRepository.findByToken(token);
        return user.isPresent() ? user.get() : null;
	}
    
    public User findByEmailAndPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.isPresent() ? user.get() : null;
	}

    public void registration(User user) {
        Optional<User> userOpt = userRepository.findByEmail(user.getEmail());
        if(userOpt.isPresent()) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        checkPassword(user.getPassword());

        save(user);
    }
    
    public void save(User user) {
        userRepository.save(user);
    }

    public void checkPassword(String password){
        if(password.length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }
    }
}
