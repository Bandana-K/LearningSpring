package com.learnWithUs.awsImageStorage.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learnWithUs.awsImageStorage.pojo.BucketName;
import com.learnWithUs.awsImageStorage.pojo.UserProfile;
import com.learnWithUs.awsImageStorage.repository.FakeUserProfileDataStore;

@Service
public class UserProfileService {
	
	@Autowired
	private FakeUserProfileDataStore userProfileDataStore;
	
	@Autowired
	private FileStorageService fileStorageService;

	public List<UserProfile> getUserProfiles() {
		return userProfileDataStore.getUserProfiles();
	}


	public void uploadUserProfileImage(String userProfileId, MultipartFile file) {
		
		isFileEmpty(file);
		
		IsImage(file);		
		
		UserProfile user = getUserProfileOrThrow(userProfileId);
		
		Map<String, String> metaData = extractMetaData(file);
		
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
		String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

		try {
			fileStorageService.save(path, fileName, Optional.of(metaData), file.getInputStream());
			user.setUserProfileImageLink(fileName);
		} catch(IOException e) {
			throw new IllegalStateException(e);
		}
	}


	public byte[] downloadUserProfileImage(String userProfileId) {
		UserProfile user = getUserProfileOrThrow(userProfileId);
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
		
		return user.getUserProfileImageLink()
				.map( key -> fileStorageService.download(path, key))
				.orElse(new byte[0]);
	}
	
	private Map<String, String> extractMetaData(MultipartFile file) {
		Map<String, String> metaData = new HashMap<>();
		metaData.put("Content-Type", file.getContentType());
		metaData.put("Content-Length", String.valueOf(file.getSize()));
		return metaData;
	}


	private UserProfile getUserProfileOrThrow(String userProfileId) {
		UUID profileId = UUID.fromString(userProfileId);
		return userProfileDataStore.getUserProfiles()
			.stream()
			.filter( userProfile -> userProfile.getUserProfileId().equals(profileId))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("No user exists with userId : " + userProfileId));
	}


	private void IsImage(MultipartFile file) {
		if(!Arrays.asList(MediaType.IMAGE_JPEG_VALUE, 
				MediaType.IMAGE_PNG_VALUE, 
				MediaType.IMAGE_GIF_VALUE).contains(file.getContentType()))
			throw new IllegalStateException("File must be an image, but the content type of the file is :" + file.getContentType());
	}


	private void isFileEmpty(MultipartFile file) {
		if(file.isEmpty())
			throw new IllegalStateException("Cannot upload empty file "+file.getSize());
	}

}
