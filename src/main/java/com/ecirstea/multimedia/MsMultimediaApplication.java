package com.ecirstea.multimedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsMultimediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMultimediaApplication.class, args);
	}

}
