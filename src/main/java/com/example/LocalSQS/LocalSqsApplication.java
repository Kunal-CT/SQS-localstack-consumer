package com.example.LocalSQS;

import io.awspring.cloud.autoconfigure.sqs.SqsAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class LocalSqsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalSqsApplication.class, args);
	}

}
