package com.pskwiercz.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.region.credentials.access-key}")
    private String key;

    @Value("${cloud.aws.region.credentials.secret-key}")
    private String secret;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 s3() {
        AWSCredentials credentials = new BasicAWSCredentials(key, secret);

        AWSStaticCredentialsProvider provider = new AWSStaticCredentialsProvider(credentials);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(provider)
                .withRegion(region)
                .build();
    }
}
