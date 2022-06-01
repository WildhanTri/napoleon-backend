
package com.example.napoleon.controller;

import java.util.List;
import java.util.UUID;

import com.example.napoleon.auth.AuthBackend;
import com.example.napoleon.firebase.FirebaseFileService;
import com.example.napoleon.model.Reportline;
import com.example.napoleon.model.User;
import com.example.napoleon.payload.ApiResponse;
import com.example.napoleon.payload.request.ReportlineRequest;
import com.example.napoleon.payload.response.ReportlineResponse;
import com.example.napoleon.payload.response.UserResponse;
import com.example.napoleon.service.ReportlineService;

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
public class ReportlineController {

	Logger log = LoggerFactory.getLogger(ReportlineController.class);

	@Autowired
	private ReportlineService reportlineService;

	@RequestMapping(value = "/v1/reportline", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getReportline(@RequestHeader String token,
			@RequestParam(value="uuidProject") String uuidProject,
			@RequestParam(value="page", required=false, defaultValue="0") Integer page,
			@RequestParam(value="offset", required=false, defaultValue="10") Integer offset,
			@RequestParam(value="sortBy", required=false, defaultValue="id") String sortBy,
			@RequestParam(value="sortType", required=false, defaultValue="asc") String sortType) {
		try {
			User user = AuthBackend.authByToken(token);
			
			List<Reportline> data = reportlineService.findReportlineByProject(uuidProject, page, offset, sortBy, sortType);

			List<ReportlineResponse> response = new ReportlineResponse().convertModelToResponseList(data);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getReportline", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportline/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getReportlineDetail(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid) {
		try {
			User user = AuthBackend.authByToken(token);
			
			Reportline data = reportlineService.findByUuid(uuid);

			ReportlineResponse response = new ReportlineResponse().convertModelToResponse(data);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getReportlineDetail", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportline", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> saveReportline(@RequestHeader String token,
			@RequestBody ReportlineRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			
			Reportline reportline = new Reportline();
			reportline = reportline.requestToObject(request, reportline);
			reportline.setUuid(UUID.randomUUID().toString());
			reportline = reportlineService.save(reportline);

			ReportlineResponse response = new ReportlineResponse().convertModelToResponse(reportline);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("saveReportline", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportline/{uuid}", method = RequestMethod.PATCH)
	public ResponseEntity<ApiResponse> updateReportline(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid,
			@RequestBody ReportlineRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			
			Reportline reportline = reportlineService.findByUuid(uuid);
			if(reportline == null) {
				throw new Exception("Reportline not found");
			}

			reportlineService.validateReportline(reportline.getUuid(), user.getUuid());

			reportline = reportline.requestToObject(request, reportline);
			reportline = reportlineService.update(reportline);

			ReportlineResponse response = new ReportlineResponse().convertModelToResponse(reportline);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("updateReportline", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/reportline/{uuid}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteReportline(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid) {
		try {
			User user = AuthBackend.authByToken(token);
			
			Reportline reportline = reportlineService.findByUuid(uuid);
			if(reportline == null) {
				throw new Exception("Reportline not found");
			}
			
			reportlineService.validateReportline(reportline.getUuid(), user.getUuid());
			reportlineService.delete(uuid);

			return ResponseEntity.ok(new ApiResponse("Reportline deleted"));
		} catch (Exception x) {
			log.error("deleteReportline", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
}
