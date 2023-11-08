package com.pskwiercz.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class S3Service {

    private final AmazonS3 s3;

    @Value("${source.bucket-name}")
    private String bucket;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.s3 = amazonS3;
    }

    public String uploadObject(String key, MultipartFile file) throws Exception {
        validateKey(key);

        // create custom metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.addUserMetadata("key1", "value1");
        metadata.addUserMetadata("key2", "value2");

        // create a PutObjectRequest with custom metadata
        PutObjectRequest request = new PutObjectRequest(bucket, key, file.getInputStream(), metadata);

        // Upload the object to S3 with custom metadata
        s3.putObject(request);
        return "done";
    }


    private void validateKey(String key) throws Exception {
        List<String> keys = s3.listObjectsV2(bucket).getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .toList();

        if (!keys.contains(key)) {
            throw new Exception(key + "_DOES_NOT_EXISTS");
        }
    }
}

