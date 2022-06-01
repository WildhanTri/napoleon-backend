package com.example.napoleon.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ProjectRequest {
	@JsonInclude(value = Include.NON_NULL)
    private String name;
	@JsonInclude(value = Include.NON_NULL)
    private String description;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
