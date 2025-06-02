package com.login_logout.service;


import com.login_logout.entity.UserDtls;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


public interface UserService {

	public UserDtls createUser(UserDtls user);

	public boolean checkEmail(String email);

	public String getUserName(OAuth2AuthenticationToken authentication);

}