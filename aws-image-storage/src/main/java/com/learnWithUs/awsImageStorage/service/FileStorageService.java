package com.learnWithUs.awsImageStorage.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

@Service
public class FileStorageService {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public void save (String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inStream) {
		
		ObjectMetadata metaData = new ObjectMetadata();
		optionalMetaData.ifPresent(map -> {
			if(!map.isEmpty()) {
				map.forEach((key, value) -> metaData.addUserMetadata(key, value));
			}
		});
		
		try {
			amazonS3.putObject(path, fileName, inStream, metaData);
		} catch(AmazonServiceException e) {
			throw new IllegalStateException("Failed to save the file to S3 ",e);
		}
	}

	public byte[] download(String path, String key) {
		try {
			S3Object image = amazonS3.getObject(path, key);
			return IOUtils.toByteArray(image.getObjectContent());
		} catch(AmazonServiceException | IOException e) {
			throw new IllegalStateException("Image file couldn't be downloaded ", e);
		}
	}

}
