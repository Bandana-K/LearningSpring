package com.learnWithUs.awsImageStorage.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.learnWithUs.awsImageStorage.pojo.UserProfile;
import com.learnWithUs.awsImageStorage.service.UserProfileService;


@RestController
@RequestMapping("user-profiles")
@CrossOrigin("*")
public class UserProfileController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	@GetMapping
	public List<UserProfile> getUserProfiles(){
		return userProfileService.getUserProfiles();	
	}
	
	@PostMapping(
			path = "/{userProfileId}/image/upload",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public void uploadUserProfileImage(@PathVariable("userProfileId") String userProfileId, 
					@RequestParam("file") MultipartFile file) {
		userProfileService.uploadUserProfileImage(userProfileId, file);
	}
	
	@GetMapping("/{userProfileId}/image/download")
	public byte[] downloadUserProfileImage(@PathVariable("userProfileId") String userProfileId) {
		return userProfileService.downloadUserProfileImage(userProfileId);
	}
	
	
}
