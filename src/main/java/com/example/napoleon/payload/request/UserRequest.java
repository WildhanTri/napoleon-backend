package com.example.napoleon.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class UserRequest {
	@JsonInclude(value = Include.NON_NULL)
    private String username;
	@JsonInclude(value = Include.NON_NULL)
    private String email;
	@JsonInclude(value = Include.NON_NULL)
    private String password;
	@JsonInclude(value = Include.NON_NULL)
    private String token;
	@JsonInclude(value = Include.NON_NULL)
    private String profilePicture;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

    
}
