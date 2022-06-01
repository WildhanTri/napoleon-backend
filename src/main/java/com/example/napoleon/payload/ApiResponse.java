package com.example.napoleon.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ApiResponse{
	@JsonInclude(value = Include.NON_NULL)
	private Boolean success;
	@JsonInclude(value = Include.NON_NULL)
	private String message;
	@JsonInclude(value = Include.NON_NULL)
	private Object data;
	
	public ApiResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public ApiResponse(Boolean success, String message, Object obj) {
		this.success = success;
		this.message = message;
		this.setData(obj);
	}
	
	public ApiResponse(Object obj) {
		this.setData(obj);
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
