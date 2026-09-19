package com.ashim.linkedinClone.postsService.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    // Intercepts incoming HTTP requests hitting this specific microservice before they reach your controllers,

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // from the request get userId
        String userId = request.getHeader("X-User-Id" );
        AuthContextHolder.setCurrentUserId(Long.valueOf(userId)); // stores it in the context holder
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Crucial: Always clear ThreadLocal to prevent memory leaks in thread pools!
        AuthContextHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}


/*

RequestInterceptor (Spring MVC HandlerInterceptor)
• What it does: Intercepts incoming HTTP requests hitting this specific microservice before they reach your controllers, and handles cleanup after the request completes.
• Individual Responsibility:
13. Extraction & Storage: Reads the incoming X-User-Id header from the incoming request and saves it into the thread-safe AuthContextHolder so the current thread knows who is making the request.
14. Memory Leak Prevention (afterCompletion): Crucially, it clears out the ThreadLocal storage once the request finishes processing. Because application servers reuse threads from a thread pool, failing to clear this would cause user contexts to bleed over and leak into subsequent requests handled by the same thread.


RequestInterceptor combined with AuthContextHolder is the exact, industry-standard pattern to avoid parameter drilling across your controllers and service layers.

Why this setup is great:
Clean Controllers: Your controller methods don't need clutter like @RequestHeader("X-User-Id") anymore.

Global Access: Any service or repository method can grab the user ID on demand using AuthContextHolder.getCurrentUserId().

Safe from Memory Leaks: Implementing the afterCompletion method to call AuthContextHolder.clear() prevents thread-pool contamination across different user requests.

What to check next to make sure it runs smoothly:
Make sure AuthContextHolder is implemented correctly using ThreadLocal<Long> with static getter, setter, and remove (clear) methods.

Register the Interceptor in a WebMvcConfigurer configuration class so Spring actually loads and executes it on incoming requests (as shown in Step 3 of the previous guide).

Double-check your API Gateway AuthenticationFilter to ensure it's successfully populating and forwarding the X-User-Id header down to this service.
 */