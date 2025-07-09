package com.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Autowired
    private AuthenticationFilter authFilter;
    
    @Autowired
    private RateLimitingFilter rateLimitingFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Authentication service routes - with rate limiting for login endpoints
                .route("auth-login", r -> r
                        .path("/auth/login", "/login")
                        .filters(f -> f.filter(rateLimitingFilter.apply(createStrictRateLimitConfig())))
                        .uri("lb://APPOINTMENT-UI"))
                
                // Other authentication service routes - no auth needed for register
                .route("auth-service", r -> r
                        .path("/auth/**", "/register/**", "/registerDoctor/**", "/signin/**", "/css/**", "/js/**")
                        .uri("lb://APPOINTMENT-UI"))
                
                // Patient service routes - requires authentication
                .route("patient-service", r -> r
                        .path("/patient/**")
                        .filters(f -> f
                                .filter(rateLimitingFilter.apply(createDefaultRateLimitConfig()))
                                .filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://MICRO-PATIENT"))
                
                // Doctor service routes - requires authentication
                .route("doctor-service", r -> r
                        .path("/doctor/**")
                        .filters(f -> f
                                .filter(rateLimitingFilter.apply(createDefaultRateLimitConfig()))
                                .filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://MICRO-DOCTOR"))
                
                // Appointment service routes - requires authentication
                .route("appointment-service", r -> r
                        .path("/appointment/**")
                        .filters(f -> f
                                .filter(rateLimitingFilter.apply(createDefaultRateLimitConfig()))
                                .filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://MICRO-APPOINTMENT"))
                
                // Medication service routes - requires authentication
                .route("medication-service", r -> r
                        .path("/medication/**")
                        .filters(f -> f
                                .filter(rateLimitingFilter.apply(createDefaultRateLimitConfig()))
                                .filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://MICRO-MEDICATION"))
                
                // Mail service routes - requires authentication
                .route("mail-service", r -> r
                        .path("/mail/**")
                        .filters(f -> f
                                .filter(rateLimitingFilter.apply(createDefaultRateLimitConfig()))
                                .filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://MICRO-MAIL"))
                
                // Default UI routes
                .route("ui-service", r -> r
                        .path("/**")
                        .uri("lb://APPOINTMENT-UI"))
                
                .build();
    }
    
    private RateLimitingFilter.Config createStrictRateLimitConfig() {
        RateLimitingFilter.Config config = new RateLimitingFilter.Config();
        config.setMaxRequests(5); // Only 5 login attempts per minute
        config.setWindowSizeMinutes(1);
        return config;
    }
    
    private RateLimitingFilter.Config createDefaultRateLimitConfig() {
        RateLimitingFilter.Config config = new RateLimitingFilter.Config();
        config.setMaxRequests(100); // 100 requests per minute
        config.setWindowSizeMinutes(1);
        return config;
    }
}
