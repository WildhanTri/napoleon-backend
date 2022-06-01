package com.example.napoleon.payload.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.napoleon.model.Reportline;
import com.example.napoleon.model.ReportlineType;
import com.example.napoleon.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReportlineResponse {
    private String uuid;
    private String name;
    private String description;
	private Integer sequence;
	private ReportlineType type;
    
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
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public ReportlineType getType() {
		return type;
	}
	public void setType(ReportlineType type) {
		this.type = type;
	}
	public ReportlineResponse convertModelToResponse(Reportline reportline){
        Map<String, Object> source = ObjectUtil.convertObjectToMap(this);
        Map<String, Object> target = ObjectUtil.convertObjectToMap(reportline);
        ObjectMapper objectMapper = new ObjectMapper();
        ReportlineResponse response = objectMapper.convertValue(ObjectUtil.patchObjectBySource(source, target), ReportlineResponse.class);
        return response;
    }

	public List<ReportlineResponse> convertModelToResponseList(List<Reportline> reportlines){
		List<ReportlineResponse> response = new ArrayList<>();
		for(Reportline reportline : reportlines){
			response.add(convertModelToResponse(reportline));
		}
        return response;
    }
}
