package com.example.napoleon.payload.request;

import com.example.napoleon.model.User;

public class LoginRequest extends User {
    private String password;
    private String username;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
