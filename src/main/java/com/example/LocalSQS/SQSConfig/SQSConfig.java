package com.example.LocalSQS.SQSConfig;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSConfig {

    private static final String LOCAL_SQS_ENDPOINT = "http://sqs.us-east-1.localhost.localstack.cloud:4566";
    private static final String ACCESS_KEY = "test";
    private static final String SECRET_KEY = "test";

    @Bean
    public AmazonSQS amazonSQS() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        return AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(LOCAL_SQS_ENDPOINT, "us-east-1"))
                .build();
    }
}
