package com.learnWithUs.awsImageStorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {
	
	@Bean
	public AmazonS3 s3() {
		
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIATKEJS4SCEIPKTB56",	"46mIOv9+6f/oAwdtD5K5WoI+s8ZCyh4630MyTifS");  //Get the appKey and secretKey for your s3 account
		
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(Regions.AP_SOUTH_1)
				.build();
	
	}

}
