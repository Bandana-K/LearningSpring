package com.learnWithUs.awsImageStorage.pojo;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
	
	private final UUID userProfileId;
	private final String userName;
	private String userProfileImageLink;		//s3 key

	public UserProfile(UUID userProfileid, String userName, String userProfileImageLink) {
		this.userProfileId = userProfileid;
		this.userName = userName;
		this.userProfileImageLink = userProfileImageLink;
	}

	public String getUserName() {
		return userName;
	}
	public UUID getUserProfileId() {
		return userProfileId;
	}
	
	public Optional<String> getUserProfileImageLink() {
		return Optional.ofNullable(userProfileImageLink);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userProfileId, userName, userProfileImageLink);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UserProfile that = (UserProfile) obj;
		
		return Objects.equals(userProfileId, that.userProfileId) &&
				Objects.equals(userName, that.userName) &&
				Objects.equals(userProfileImageLink, that.userProfileImageLink);
	}

	public void setUserProfileImageLink(String userProfileImageLink) {
		this.userProfileImageLink = userProfileImageLink;
	}

}
