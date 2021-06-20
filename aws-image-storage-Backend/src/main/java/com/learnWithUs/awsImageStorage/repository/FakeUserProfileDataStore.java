package com.learnWithUs.awsImageStorage.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.learnWithUs.awsImageStorage.pojo.UserProfile;

@Repository
public class FakeUserProfileDataStore {
	
	private static final List<UserProfile> userProfile = new ArrayList<>();
	
	static {
		userProfile.add(new UserProfile(UUID.fromString("13e82d77-0195-4fe1-b2f6-6e87caa9f9c9"), "Bandana Kumari", null));
		userProfile.add(new UserProfile(UUID.fromString("57576f89-7080-47a8-8050-482ae99f0243"), "Prakash Kumar", null));
	}
	
	public List<UserProfile> getUserProfiles(){
		return userProfile;
	}

}
