package com.catsoft.demo.icecreamparlor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class IceCreamParlorMenuServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IceCreamParlorMenuServiceApplication.class, args);
	}
}
