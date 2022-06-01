package com.example.napoleon.payload.request;

import com.example.napoleon.model.ReportlineType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ReportlineRequest {
    
	@JsonInclude(value = Include.NON_NULL)
    private String uuid;
	@JsonInclude(value = Include.NON_NULL)
    private String name;
	@JsonInclude(value = Include.NON_NULL)
    private String description;
	@JsonInclude(value = Include.NON_NULL)
    private String sequence;
	@JsonInclude(value = Include.NON_NULL)
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
    public String getSequence() {
        return sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

}
