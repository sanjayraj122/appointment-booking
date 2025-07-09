package com.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RateLimitingFilter extends AbstractGatewayFilterFactory<RateLimitingFilter.Config> implements Ordered {

    private final ConcurrentMap<String, RateLimitInfo> rateLimitMap = new ConcurrentHashMap<>();
    
    public RateLimitingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String clientIp = getClientIp(exchange);
            String key = clientIp + ":" + exchange.getRequest().getPath().value();
            
            RateLimitInfo rateLimitInfo = rateLimitMap.computeIfAbsent(key, k -> new RateLimitInfo());
            
            LocalDateTime now = LocalDateTime.now();
            
            // Reset counter if window has passed
            if (ChronoUnit.MINUTES.between(rateLimitInfo.getWindowStart(), now) >= config.getWindowSizeMinutes()) {
                rateLimitInfo.reset(now);
            }
            
            // Check if limit exceeded
            if (rateLimitInfo.getRequestCount() >= config.getMaxRequests()) {
                return handleRateLimitExceeded(exchange);
            }
            
            // Increment counter
            rateLimitInfo.incrementCount();
            
            // Add rate limit headers
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("X-RateLimit-Limit", String.valueOf(config.getMaxRequests()));
            response.getHeaders().add("X-RateLimit-Remaining", 
                String.valueOf(Math.max(0, config.getMaxRequests() - rateLimitInfo.getRequestCount())));
            response.getHeaders().add("X-RateLimit-Reset", 
                String.valueOf(rateLimitInfo.getWindowStart().plus(config.getWindowSizeMinutes(), ChronoUnit.MINUTES)));
            
            return chain.filter(exchange);
        };
    }
    
    private String getClientIp(ServerWebExchange exchange) {
        String xRealIp = exchange.getRequest().getHeaders().getFirst("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        String xForwardedFor = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        return exchange.getRequest().getRemoteAddress() != null ? 
            exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() : "unknown";
    }
    
    private Mono<Void> handleRateLimitExceeded(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        response.getHeaders().add("Content-Type", "application/json");
        
        String body = "{\"error\":\"Rate limit exceeded\",\"status\":429,\"message\":\"Too many requests. Please try again later.\"}";
        var buffer = response.bufferFactory().wrap(body.getBytes());
        return response.writeWith(Mono.just(buffer));
    }
    
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
    public static class Config {
        private int maxRequests = 100; // requests per window
        private int windowSizeMinutes = 1; // window size in minutes
        
        public int getMaxRequests() {
            return maxRequests;
        }
        
        public void setMaxRequests(int maxRequests) {
            this.maxRequests = maxRequests;
        }
        
        public int getWindowSizeMinutes() {
            return windowSizeMinutes;
        }
        
        public void setWindowSizeMinutes(int windowSizeMinutes) {
            this.windowSizeMinutes = windowSizeMinutes;
        }
    }
    
    private static class RateLimitInfo {
        private int requestCount;
        private LocalDateTime windowStart;
        
        public RateLimitInfo() {
            this.requestCount = 0;
            this.windowStart = LocalDateTime.now();
        }
        
        public void incrementCount() {
            this.requestCount++;
        }
        
        public void reset(LocalDateTime newWindowStart) {
            this.requestCount = 0;
            this.windowStart = newWindowStart;
        }
        
        public int getRequestCount() {
            return requestCount;
        }
        
        public LocalDateTime getWindowStart() {
            return windowStart;
        }
    }
}