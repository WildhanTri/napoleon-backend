package com.example.napoleon.payload.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.napoleon.model.ReportData;
import com.example.napoleon.model.Reportline;
import com.example.napoleon.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReportDataResponse {
    private String uuid;
    private String value;
    private String note;
	private Reportline reportline;
    
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Reportline getReportline() {
		return reportline;
	}

	public void setReportline(Reportline reportline) {
		this.reportline = reportline;
	}

	public ReportDataResponse convertModelToResponse(ReportData reportData){
        Map<String, Object> source = ObjectUtil.convertObjectToMap(this);
        Map<String, Object> target = ObjectUtil.convertObjectToMap(reportData);
        ObjectMapper objectMapper = new ObjectMapper();
        ReportDataResponse response = objectMapper.convertValue(ObjectUtil.patchObjectBySource(source, target), ReportDataResponse.class);
        return response;
    }

	public List<ReportDataResponse> convertModelToResponseList(List<ReportData> reportDatas){
		List<ReportDataResponse> response = new ArrayList<>();
		for(ReportData reportData : reportDatas){
			response.add(convertModelToResponse(reportData));
		}
        return response;
    }
}
