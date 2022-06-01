package com.example.napoleon.payload.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.napoleon.model.Project;
import com.example.napoleon.model.User;
import com.example.napoleon.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectResponse {
    private String uuid;
    private String name;
    private String description;
    
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
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
	public ProjectResponse convertModelToResponse(Project project){
        Map<String, Object> source = ObjectUtil.convertObjectToMap(this);
        Map<String, Object> target = ObjectUtil.convertObjectToMap(project);
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectResponse response = objectMapper.convertValue(ObjectUtil.patchObjectBySource(source, target), ProjectResponse.class);
        return response;
    }

	public List<ProjectResponse> convertModelToResponseList(List<Project> projects){
		List<ProjectResponse> response = new ArrayList<>();
		for(Project project : projects){
			response.add(convertModelToResponse(project));
		}
        return response;
    }
}
