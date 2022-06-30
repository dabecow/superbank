package com.example.superbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SuperbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperbankApplication.class, args);
	}

}
