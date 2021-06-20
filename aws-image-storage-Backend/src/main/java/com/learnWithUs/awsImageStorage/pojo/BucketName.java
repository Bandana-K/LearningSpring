package com.learnWithUs.awsImageStorage.pojo;

public enum BucketName {
	
	PROFILE_IMAGE("learnwithus-awsimagestorage"); //get the name of the s3 bucket created
	
	private final String bucketName;	

	BucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public Object getBucketName() {
		return bucketName;
	}

}
