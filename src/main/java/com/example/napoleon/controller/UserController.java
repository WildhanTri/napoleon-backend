
package com.example.napoleon.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.napoleon.auth.AuthBackend;
import com.example.napoleon.firebase.FirebaseFileService;
import com.example.napoleon.model.User;
import com.example.napoleon.payload.ApiResponse;
import com.example.napoleon.payload.request.LoginRequest;
import com.example.napoleon.payload.request.RegistrationRequest;
import com.example.napoleon.payload.request.TokenRequest;
import com.example.napoleon.payload.request.UploadProfilePictureRequest;
import com.example.napoleon.payload.request.UserChangePasswordRequest;
import com.example.napoleon.payload.request.UserForgotPasswordRequest;
import com.example.napoleon.payload.request.UserRequest;
import com.example.napoleon.payload.response.UserResponse;
import com.example.napoleon.service.UserService;
import com.example.napoleon.util.AppUtil;
import com.example.napoleon.util.FileUtil;
import com.example.napoleon.util.JWTUtility;
import com.example.napoleon.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Value("${firebase.credentials.path}")
	private String firebaseCredentialsPath;

	@Autowired
	private UserService userService;
	
    @Autowired
    private FirebaseFileService firebaseFileService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> registration(@RequestHeader String token,
			@RequestBody RegistrationRequest request) {
		try {
			User user = new User();
			user.setUuid(UUID.randomUUID().toString());
			user.setUsername(request.getUsername());
			user.setEmail(request.getEmail());
			user.setPassword(request.getPassword());
			user.setLastLogin(new Date());

			userService.registration(user);

			user = userService.findByUuid(user.getUuid());
			UserResponse response = new UserResponse().convertModelToResponse(user);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("registration", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
		try {
			User user = AuthBackend.authByEmailAndPassword(request.getEmail(), request.getPassword());

			user.setLastLogin(new Date());

			System.out.println(firebaseCredentialsPath);
			
			String newToken = JWTUtility.createJWT("" + user.getUuid(), "ecommerce", "user", -1);
			user.setToken(newToken);

			userService.save(user);

			UserResponse userResponse = new UserResponse().convertModelToResponse(user);

			Map response = new HashMap<>();
			response.put("user", userResponse);
			response.put("token", newToken);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("login", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> changePassword(@RequestHeader String token, @RequestBody UserChangePasswordRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			
			if (!user.getPassword().equals(request.getOldPassword())){
				throw new Exception("Old password is not correct");
			}

			user.setPassword(request.getNewPassword());

			userService.save(user);

			return ResponseEntity.ok(new ApiResponse("Password Changed"));
		} catch (Exception x) {
			log.error("login", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/forgotPasswordRequest", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> forgotPasswordRequest(@RequestHeader String token) {
		try {
			User user = AuthBackend.authByToken(token);
			
			user.setPasswordOtp(AppUtil.randomOtp());
			user.setPasswordOtpExpired(AppUtil.addMinutes(new Date(), 30));

			userService.save(user);

			return ResponseEntity.ok(new ApiResponse("otp send"));
		} catch (Exception x) {
			log.error("login", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/forgotPasswordConfirm", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> forgotPasswordConfirm(@RequestHeader String token, @RequestBody UserForgotPasswordRequest request) {
		try {
			User user = AuthBackend.authByToken(token);
			if(user.getPasswordOtp() == null || user.getPasswordOtpExpired() == null) {
				throw new Exception("Ask otp first");
			}

			if(AppUtil.isExpired(user.getPasswordOtpExpired())) {
				throw new Exception("Otp is expired");
			}

			if(!user.getPasswordOtp().equals(request.getOtp())) {
				throw new Exception("Otp is not correct");
			}

			user.setPassword(request.getNewPassword());

			userService.save(user);

			return ResponseEntity.ok(new ApiResponse("password changed"));
		} catch (Exception x) {
			log.error("login", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}

	@RequestMapping(value = "/profile/getByToken", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse> getByToken(@RequestHeader String token) {
		try {
			User user = AuthBackend.authByToken(token);
			if(user == null) {
				throw new Exception("Invalid token");
			}

			UserResponse response = new UserResponse().convertModelToResponse(user);

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("getByToken", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}
	
	@RequestMapping(value = "/profile/picture", method = RequestMethod.PATCH)
	public ResponseEntity<ApiResponse> updateProfilePicture(@RequestHeader String token,
			@ModelAttribute UploadProfilePictureRequest request) {
		try {
			User user = AuthBackend.authByToken(token);

			// CHECK EXTENSION
			if(!firebaseFileService.getExtension(request.getPicture().getOriginalFilename()).contains("jpg") && !firebaseFileService.getExtension(request.getPicture().getOriginalFilename()).contains("png")) {
				throw new Exception("Invalid file extension");
			}

			String profilePictureFileName = "profile_picture_" + user.getUuid() + ".jpg";
			String profilePictureUrl = firebaseFileService.saveToFirebaseStorage("user-profile-picture/",profilePictureFileName, request.getPicture());

			if(profilePictureUrl == null) {
				throw new Exception("Failed to upload file");
			}

			user.setProfilePicture(profilePictureUrl);
			userService.save(user);

			UserResponse response = new UserResponse().convertModelToResponse(user);

			// String path = "/uploads/profile/" + user.getUuid();
			// if (request.getPicture() != null) {
			// 	File dir = new File(path);
			// 	if (!dir.exists()) {
			// 		dir.mkdirs();
			// 	}

			// 	File serverFile = new File(dir.getAbsolutePath() + File.separator + user.getUuid() + "."
			// 			+ FileUtil.getFileExtension(request.getPicture()));

			// 	FileUtil.writeImage(request.getPicture(), serverFile);
			// }

			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("updateProfilePicture", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}

	@RequestMapping(value = "/profile", method = RequestMethod.PATCH)
	public ResponseEntity<ApiResponse> updateProfile(@RequestHeader String token,
			@RequestBody UserRequest request) {
		try {
			User user = AuthBackend.authByToken(token);

			Map<String, Object> source = ObjectUtil.convertObjectToMap(user);
			Map<String, Object> target = ObjectUtil.convertObjectToMap(request);

			ObjectMapper objectMapper = new ObjectMapper();
			user = objectMapper.convertValue(ObjectUtil.patchObjectByTarget(source, target), User.class);

			userService.save(user);

			UserResponse response = new UserResponse().convertModelToResponse(user);
			return ResponseEntity.ok(new ApiResponse(response));
		} catch (Exception x) {
			log.error("updateProfile", x);
			return ResponseEntity.badRequest().body(new ApiResponse(x.getMessage()));
		}
	}

}
