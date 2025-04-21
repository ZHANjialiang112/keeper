package com.zjl.keeper.core.context;

/**
 * @author wenman
 */
public class UserContextHolder {

    public static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static User get() {
        return USER_THREAD_LOCAL.get();
    }

    public static String getUserName() {
        return USER_THREAD_LOCAL.get().getUsername();
    }
}
