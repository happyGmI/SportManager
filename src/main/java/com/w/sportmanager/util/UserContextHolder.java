package com.w.sportmanager.util;

public class UserContextHolder {
    private static final ThreadLocal<String> currentUserId = new ThreadLocal<String>();

    public static void setCurrentUserId(String userId){
        currentUserId.set(userId);
    }

    public static String getCurrentUserId(){
        return currentUserId.get();
    }

    public static void clearCurrentUserId(){
        currentUserId.get();
    }
}
