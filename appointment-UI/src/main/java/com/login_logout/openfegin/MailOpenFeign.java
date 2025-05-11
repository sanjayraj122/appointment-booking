package com.login_logout.openfegin;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "micro-mail")
public interface MailOpenFeign {


}
