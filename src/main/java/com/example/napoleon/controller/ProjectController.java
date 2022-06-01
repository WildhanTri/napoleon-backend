
package com.example.napoleon.controller;

import java.util.List;
import java.util.UUID;

import com.example.napoleon.auth.AuthBackend;
import com.example.napoleon.firebase.FirebaseFileService;
import com.example.napoleon.model.Project;
import com.example.napoleon.model.User;
import com.example.napoleon.payload.ApiResponse;
import com.example.napoleon.payload.request.ProjectRequest;
import com.example.napoleon.payload.response.ProjectResponse;
import com.example.napoleon.payload.response.UserResponse;
import com.example.napoleon.service.ProjectService;

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
public class ProjectController {

	Logger log = LoggerFactory.getLogger(ProjectController.class);

	@Value("${firebase.credentials.path}")
	private String firebaseCredentialsPath;

	@Autowired
	private ProjectService projectService;
	
    @Autowired
    private FirebaseFileService firebaseFileService;

	@RequestMapping(value = "/v1/project", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getProjectDetail(@RequestHeader String token,
			@RequestParam(value="page", required=false, defaultValue="0") Integer page,
			@RequestParam(value="offset", required=false, defaultValue="10") Integer offset,
			@RequestParam(value="sortBy", required=false, defaultValue="id") String sortBy,
			@RequestParam(value="sortType", required=false, defaultValue="asc") String sortType) {
		try {
			User user = AuthBackend.authByToken(token);
			
			List<Project> data = projectService.findProjectByUser(user.getUuid(), page, offset, sortBy, sortType);

			List<ProjectResponse> response = new ProjectResponse().convertModelToResponseList(data);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getProject", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/project/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getProject(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid) {
		try {
			User user = AuthBackend.authByToken(token);
			
			Project data = projectService.findByUuid(uuid);

			ProjectResponse response = new ProjectResponse().convertModelToResponse(data);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getByToken", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/project", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> saveProject(@RequestHeader String token,
			@RequestBody ProjectRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			
			Project project = new Project();
			project = project.requestToObject(request, project);
			project.setUuid(UUID.randomUUID().toString());
			project.setUser(user);
			project = projectService.save(project);

			ProjectResponse response = new ProjectResponse().convertModelToResponse(project);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("saveProject", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/project/{uuid}", method = RequestMethod.PATCH)
	public ResponseEntity<ApiResponse> updateProject(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid,
			@RequestBody ProjectRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			
			Project project = projectService.findByUuid(uuid);
			if(project == null) {
				throw new Exception("Project not found");
			}

			projectService.validateProject(project.getUuid(), user.getUuid());

			project = project.requestToObject(request, project);
			project = projectService.update(project);

			ProjectResponse response = new ProjectResponse().convertModelToResponse(project);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("updateProject", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/v1/project/{uuid}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse> deleteProject(@RequestHeader String token,
			@PathVariable(value="uuid") String uuid,
			@RequestBody ProjectRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			
			Project project = projectService.findByUuid(uuid);
			if(project == null) {
				throw new Exception("Project not found");
			}
			
			projectService.validateProject(project.getUuid(), user.getUuid());
			projectService.delete(uuid);

			return ResponseEntity.ok(new ApiResponse("Project deleted"));
		} catch (Exception x) {
			log.error("deleteProject", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
}
