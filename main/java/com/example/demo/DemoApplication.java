package com.example.demo;

import com.example.demo.util.AppConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean(name = "uploadPath")
	public String uploadPath() {
		return AppConstants.ATTACH_DIR_NAME;
	}
}
