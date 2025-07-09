package com.doctor.filter;

import com.doctor.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Check for user information passed from API Gateway
        String userId = request.getHeader("X-User-Id");
        String userRole = request.getHeader("X-User-Role");

        // If gateway didn't provide user info, try to extract from Authorization header
        if (userId == null || userRole == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    String token = authHeader.substring(7);
                    if (jwtUtil.validateToken(token)) {
                        userId = jwtUtil.extractUsername(token);
                        userRole = jwtUtil.extractRole(token);
                    }
                } catch (Exception e) {
                    logger.error("Token validation failed: {}", e.getMessage());
                }
            }
        }

        // Set authentication context if user info is available
        if (userId != null && userRole != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Create authorities based on role
                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + userRole.toUpperCase())
                );

                // Create authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userId, null, authorities);

                // Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
                
                logger.debug("Set authentication for user: {} with role: {}", userId, userRole);
            } catch (Exception e) {
                logger.error("Failed to set authentication: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator") || path.startsWith("/health");
    }
}