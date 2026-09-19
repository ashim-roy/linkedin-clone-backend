package com.ashim.linkedinClone.postsService.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;


@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        Long userId = AuthContextHolder.getCurrentUserId();
        if(userId != null) {
            requestTemplate.header("X-User-Id", userId.toString());
        }

    }
}

/*
• What it does: Implements Feign's RequestInterceptor interface. Every time your application makes an outgoing HTTP request to another microservice using an OpenFeign client, this interceptor intercepts the outgoing request before it leaves.
• Individual Responsibility: It grabs the currently logged-in user's ID from your local thread context (AuthContextHolder) and injects it into the outgoing request's HTTP headers under the key X-User-Id. This is what allows user context to travel seamlessly across service boundaries without parameter drilling.

 */