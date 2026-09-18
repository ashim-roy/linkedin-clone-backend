package com.ashimCS.linkedinClone.postsService.auth;

public class AuthContextHolder {

    //a utility class using ThreadLocal to safely hold and clear the user ID for the current thread.

    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        return currentUserId.get();
    }

    static void setCurrentUserId(Long userId) {
        currentUserId.set(userId);
    }

    // we are not making setter as public. Becozx its package private. Class with this auth package will be able to set the user id.
    // No. one else camnn. I will have a interceptor that will be able to set the userid to the userDI that it gets form headers.

    public static void clear() {
        currentUserId.remove();
    }

    // I will have a clear --> to remove form memory so we are not leaking any memory,
    // I will call the method once the job of this req is done and all of this can be requestInterceptors

}
