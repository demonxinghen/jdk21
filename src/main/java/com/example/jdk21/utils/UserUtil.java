package com.example.jdk21.utils;


import com.example.jdk21.model.User;

/**
 * @author admin
 * @date 2023/11/21 14:54
 */
public class UserUtil {

    private UserUtil() {
    }

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static String getCurrentUserId() {
        return LOCAL.get() == null ? null : LOCAL.get().getId();
    }


    public static void put(User user) {
        LOCAL.set(user);
    }

    public static User get() {
        return LOCAL.get();
    }

    public static String getUsername() {
        return LOCAL.get() == null ? null : LOCAL.get().getUsername();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
