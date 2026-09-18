package com.ashim.linkedinClone.APIGateway.filter;

import com.ashim.linkedinClone.APIGateway.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Auth request: {}", exchange.getRequest().getURI());

            final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            // Fix: Reject if null OR if it DOES NOT start with Bearer
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Extract the actual token after "Bearer "
            final String token = tokenHeader.split("Bearer ")[1];
            log.info("Extracted JWT Token successfully");


            // JWT Validation & Request Mutation
            try{
                String userId = String.valueOf(jwtService.getUserIdFromToken(token));

                // Mutate request to add user ID header for downstream microservices
                ServerWebExchange mutatedExchange =  exchange.mutate()
                        .request(r -> r.header("X-User-Id", userId))
                        .build();

                return chain.filter(mutatedExchange);   // <-- Returns here on success

            }catch (JwtException e){
                log.error("JWT Exception {}", e.getLocalizedMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();    // <-- Returns here on error
            }

        };
    }

    static class Config {

    }

}
