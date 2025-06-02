package com.login_logout.config;

import com.login_logout.entity.Patient;
import com.login_logout.entity.UserDtls;
import com.login_logout.repo.PatientRepo;
import com.login_logout.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component("oAuthAuthenticationSuccessHandler")
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepo patientRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        String email = (String) oauthUser.getAttribute("email");
        String name = (String) oauthUser.getAttribute("name");

        // Example: assign doctor role if email ends with @doctor.com
        String role = (email != null && email.endsWith("@doctor.com")) ? "ROLE_DOCTOR" : "ROLE_PATIENT";
        String redirectUrl = role.equals("ROLE_DOCTOR") ? "/doctor/" : "/patient/";

        // Check if user already exists
        UserDtls existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            logger.info("User already exists with email: " + email);

            // Add user's role to authorities if not present
            Collection<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
            if (updatedAuthorities.stream().noneMatch(a -> a.getAuthority().equals(existingUser.getRole()))) {
                updatedAuthorities.add(new SimpleGrantedAuthority(existingUser.getRole()));
                OAuth2AuthenticationToken newAuth = new OAuth2AuthenticationToken(
                        (OAuth2User) authentication.getPrincipal(),
                        updatedAuthorities,
                        oauth2Token.getAuthorizedClientRegistrationId()
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }

            new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
            return;
        }

        // Create and save UserDtls
        UserDtls user = new UserDtls();
        user.setEmail(email);
        user.setPassword("dummy"); // Set a dummy password
        user.setRole(role);
        userRepository.save(user);

        // Create and save Patient if role is PATIENT
        if (role.equals("ROLE_PATIENT")) {
            Patient patient = new Patient();
            patient.setEmail(email);
            patient.setAddress(patient.getAddress());
            patient.setPassword(patient.getPassword());
            patient.setFullName(name);
            patientRepository.save(patient);
        }
        // For ROLE_DOCTOR, add doctor entity creation if needed

//        UserDtls user2 = new UserDtls();
//        user.setEmail(doctor.getEmail());
//        user.setPassword(doctor.getPassword());
//        user.setRole("ROLE_DOCTOR");
//        logger.info("New OAuth user saved: {} with role {}", email, role);

        // Add role to authorities
        Collection<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority(role));
        OAuth2AuthenticationToken newAuth = new OAuth2AuthenticationToken(
                (OAuth2User) authentication.getPrincipal(),
                updatedAuthorities,
                oauth2Token.getAuthorizedClientRegistrationId()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}