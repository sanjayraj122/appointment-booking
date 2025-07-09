package com.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/auth/logout", 
            "/auth/register",
            "/register",
            "/registerDoctor",
            "/signin",
            "/login",
            "/css",
            "/js",
            "/favicon.ico",
            "/actuator",
            "/eureka",
            // Health check endpoints
            "/health",
            "/info",
            // Public documentation endpoints (if using Swagger)
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-resources"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
