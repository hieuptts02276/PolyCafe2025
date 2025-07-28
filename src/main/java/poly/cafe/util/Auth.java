// poly.cafe.util.Auth.java
package poly.cafe.util;

import poly.cafe.entity.User;

public class Auth {
    public static User user = null; // Biến tĩnh để lưu trữ người dùng hiện tại

    public static boolean isLogin() {
        return user != null;
    }

    public static void clear() {
        user = null;
    }

    public static boolean isManager() {
        return isLogin() && user.isManager();
    }
}
