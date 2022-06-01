
package com.example.napoleon.controller;

import java.util.List;
import java.util.UUID;

import com.example.napoleon.auth.AuthBackend;
import com.example.napoleon.firebase.FirebaseFileService;
import com.example.napoleon.model.ReportlineType;
import com.example.napoleon.model.User;
import com.example.napoleon.payload.ApiResponse;
import com.example.napoleon.payload.response.ReportlineTypeResponse;
import com.example.napoleon.payload.response.UserResponse;
import com.example.napoleon.service.ReportlineService;
import com.example.napoleon.service.ReportlineTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReportlineTypeController {

	Logger log = LoggerFactory.getLogger(ReportlineTypeController.class);

	@Autowired
	private ReportlineTypeService reportlineTypeService;

	@RequestMapping(value = "/v1/reportlineType", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getReportline(@RequestHeader String token,
			@RequestParam(value="page", required=false, defaultValue="0") Integer page,
			@RequestParam(value="offset", required=false, defaultValue="10") Integer offset,
			@RequestParam(value="sortBy", required=false, defaultValue="id") String sortBy,
			@RequestParam(value="sortType", required=false, defaultValue="asc") String sortType) {
		try {
			User user = AuthBackend.authByToken(token);
			
			List<ReportlineType> data = reportlineTypeService.find(page, offset, sortBy, sortType);

			List<ReportlineTypeResponse> response = new ReportlineTypeResponse().convertModelToResponseList(data);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getReportline", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportlineType/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getReportlineDetail(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid) {
		try {
			User user = AuthBackend.authByToken(token);
			
			ReportlineType data = reportlineTypeService.findByUuid(uuid);

			ReportlineTypeResponse response = new ReportlineTypeResponse().convertModelToResponse(data);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getReportlineDetail", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
}
