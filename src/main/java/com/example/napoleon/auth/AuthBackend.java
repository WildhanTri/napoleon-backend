package com.example.napoleon.auth;

import javax.annotation.PostConstruct;

import com.example.napoleon.model.User;
import com.example.napoleon.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthBackend {
    

	private static UserService userSvc;
	@Autowired
	UserService userService;

    
	@PostConstruct
	private void initStaticDao () {
		userSvc = this.userService;
	}


    public static User authByEmailAndPassword(String email, String password){
        User user = userSvc.findByEmailAndPassword(email, password);
        if(user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    public static User authByToken(String token){
        User user = userSvc.findByToken(token);
        if(user == null) {
            throw new RuntimeException("Unauthorized");
        }

        return user;
    }
}
