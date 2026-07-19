package com.enterprise.knowledgehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class KnowledgehubApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgehubApplication.class, args);
	}

}
