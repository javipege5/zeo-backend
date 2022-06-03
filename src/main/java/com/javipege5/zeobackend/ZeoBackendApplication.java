package com.javipege5.zeobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ZeoBackendApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ZeoBackendApplication.class, args);
	}




}
