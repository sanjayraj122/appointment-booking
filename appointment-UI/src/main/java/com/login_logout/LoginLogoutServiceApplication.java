package com.login_logout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.login_logout.openfegin")
@EnableScheduling
public class LoginLogoutServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginLogoutServiceApplication.class, args);
	}

}
