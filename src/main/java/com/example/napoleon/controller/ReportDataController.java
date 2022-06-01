
package com.example.napoleon.controller;

import java.util.List;
import java.util.UUID;

import com.example.napoleon.auth.AuthBackend;
import com.example.napoleon.model.ReportData;
import com.example.napoleon.model.User;
import com.example.napoleon.payload.ApiResponse;
import com.example.napoleon.payload.request.ReportDataRequest;
import com.example.napoleon.payload.response.ReportDataResponse;
import com.example.napoleon.service.ReportDataService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReportDataController {

	Logger log = LoggerFactory.getLogger(ReportDataController.class);

	@Autowired
	private ReportDataService reportDataService;

	@RequestMapping(value = "/v1/reportData", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getReportData(@RequestHeader String token,
			@RequestParam(value="uuidProject") String uuidProject,
			@RequestParam(value="page", required=false, defaultValue="0") Integer page,
			@RequestParam(value="offset", required=false, defaultValue="10") Integer offset,
			@RequestParam(value="sortBy", required=false, defaultValue="id") String sortBy,
			@RequestParam(value="sortType", required=false, defaultValue="asc") String sortType) {
		try {
			User user = AuthBackend.authByToken(token);
			
			List<ReportData> data = reportDataService.findReportDataByProject(uuidProject, page, offset, sortBy, sortType);

			List<ReportDataResponse> response = new ReportDataResponse().convertModelToResponseList(data);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getReportData", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportData/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getReportDataDetail(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid) {
		try {
			User user = AuthBackend.authByToken(token);
			
			ReportData data = reportDataService.findByUuid(uuid);

			ReportDataResponse response = new ReportDataResponse().convertModelToResponse(data);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getReportDataDetail", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportData", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> saveReportData(@RequestHeader String token,
			@RequestBody ReportDataRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			
			ReportData reportData = new ReportData();
			reportData = reportData.requestToObject(request, reportData);
			reportData.setUuid(UUID.randomUUID().toString());
			reportData = reportDataService.save(reportData);

			ReportDataResponse response = new ReportDataResponse().convertModelToResponse(reportData);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("saveReportData", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportData/{uuid}", method = RequestMethod.PATCH)
	public ResponseEntity<ApiResponse> updateReportData(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid,
			@RequestBody ReportDataRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			
			ReportData reportData = reportDataService.findByUuid(uuid);
			if(reportData == null) {
				throw new Exception("ReportData not found");
			}

			reportData = reportData.requestToObject(request, reportData);
			reportData = reportDataService.update(reportData);

			ReportDataResponse response = new ReportDataResponse().convertModelToResponse(reportData);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("updateReportData", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportData/{uuid}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteReportData(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid) {
		try {
			User user = AuthBackend.authByToken(token);
			
			ReportData reportData = reportDataService.findByUuid(uuid);
			if(reportData == null) {
				throw new Exception("ReportData not found");
			}
			
			reportDataService.delete(uuid);

			return ResponseEntity.ok(new ApiResponse("ReportData deleted"));
		} catch (Exception x) {
			log.error("deleteReportData", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
}
