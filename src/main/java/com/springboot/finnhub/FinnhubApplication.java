package com.springboot.finnhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinnhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinnhubApplication.class, args);
	}

}
