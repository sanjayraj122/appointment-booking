
package com.login_logout.service;


import com.login_logout.entity.UserDtls;
import com.login_logout.repo.UserRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.PasswordOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String role = (String) attributes.get("role");
        String usrId = (String) attributes.get("userId");
        String password = (String) attributes.get("password");
        String login = (String) attributes.get("login");

        if (email == null && "github".equals(registrationId)) {
            email = login + "@github.com"; // fallback
        }

        UserDtls user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.save(user);
        } else {
            UserDtls newUser = new UserDtls();
            newUser.setEmail(email);
            newUser.setPassword("password");
            newUser.setRole(role);
            newUser.setId(1); // Assuming ID is auto-generated
            newUser.setRole("ROLE_PATIENT");
            // or set role as needed
            userRepository.save(newUser);
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_PATIENT")); // or set role as needed
        return new DefaultOAuth2User(authorities, attributes, "name");
    }
}

