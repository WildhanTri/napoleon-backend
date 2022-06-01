package com.example.napoleon.payload.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.napoleon.model.ReportlineType;
import com.example.napoleon.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReportlineTypeResponse {
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
	public ReportlineTypeResponse convertModelToResponse(ReportlineType reportlineType){
        Map<String, Object> source = ObjectUtil.convertObjectToMap(this);
        Map<String, Object> target = ObjectUtil.convertObjectToMap(reportlineType);
        ObjectMapper objectMapper = new ObjectMapper();
        ReportlineTypeResponse response = objectMapper.convertValue(ObjectUtil.patchObjectBySource(source, target), ReportlineTypeResponse.class);
        return response;
    }

	public List<ReportlineTypeResponse> convertModelToResponseList(List<ReportlineType> reportlineTypes){
		List<ReportlineTypeResponse> response = new ArrayList<>();
		for(ReportlineType reportlineType : reportlineTypes){
			response.add(convertModelToResponse(reportlineType));
		}
        return response;
    }
}
