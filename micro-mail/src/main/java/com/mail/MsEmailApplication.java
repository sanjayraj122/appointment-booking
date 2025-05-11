package com.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.mail.openfeign")
@EnableJpaRepositories("com.mail.repository")
@ComponentScan({"com.mail.controller", "com.mail.service","com.mail.taskScheduler"})
@EntityScan("com.mail.entity")
@EnableScheduling

public class MsEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEmailApplication.class, args);
    }

}
