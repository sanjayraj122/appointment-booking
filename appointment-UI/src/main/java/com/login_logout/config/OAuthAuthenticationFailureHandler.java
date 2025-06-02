package com.login_logout.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("oAuthAuthenticationFailureHandler")
public class OAuthAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuthAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        logger.error("OAuth2 authentication failed: {}", exception.getMessage());

        // Optionally, set an error message in session or request
        request.getSession().setAttribute("error.message", "OAuth2 login failed: " + exception.getMessage());

        // Redirect to a custom error page or login page
        response.sendRedirect("/signin?error=oauth2");
    }
}
