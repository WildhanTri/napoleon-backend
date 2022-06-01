package com.example.napoleon.payload.response;

import java.util.Map;

import com.example.napoleon.model.User;
import com.example.napoleon.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserResponse {
    private String uuid;
    private String username;
    private String email;
    private String profilePicture;
    
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
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
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
    
    public UserResponse convertModelToResponse(User user){
        Map<String, Object> source = ObjectUtil.convertObjectToMap(this);
        Map<String, Object> target = ObjectUtil.convertObjectToMap(user);
        ObjectMapper objectMapper = new ObjectMapper();
        UserResponse response = objectMapper.convertValue(ObjectUtil.patchObjectBySource(source, target), UserResponse.class);
        return response;
    }
}
