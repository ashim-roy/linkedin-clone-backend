package com.ashim.linkedinClone.postsService.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {
    //  to register your custom interceptor into the application's request-handling lifecycle

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}


/*
	• What it does: Implements Spring MVC's WebMvcConfigurer configuration interface to register your custom interceptor into the application's request-handling lifecycle.
	• Individual Responsibility: It tells Spring Boot to activate and apply your RequestInterceptor to incoming HTTP traffic so that it actively monitors and processes incoming requests.

 */