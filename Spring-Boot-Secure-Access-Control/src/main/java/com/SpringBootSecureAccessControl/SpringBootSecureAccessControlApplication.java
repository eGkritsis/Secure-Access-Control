package com.SpringBootSecureAccessControl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.SecureRandom;

@SpringBootApplication
public class SpringBootSecureAccessControlApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		/*
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
		System.out.println("salt:" + salt);

		byte[] salt2 = new byte[16];
		secureRandom.nextBytes(salt2);
		System.out.println("salt2:" + salt2);

		//salt:[B@5679c6c6
		//salt2:[B@27ddd392
		 */

		SpringApplication.run(SpringBootSecureAccessControlApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("homepage");
		registry.addViewController("/homepage.html").setViewName("homepage");
		registry.addViewController("/login.html").setViewName("login");
	}

}
