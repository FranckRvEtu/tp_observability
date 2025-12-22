package observability.context;

import observability.model.User;

public class UserContext {
    private static final ThreadLocal<String> user = new ThreadLocal<>();

    public static void setCurrentUser(String userId) {
        user.set(userId);
    }

    public static String getCurrentUser() {
        return user.get();
    }

    public static void clear() {
        user.remove();
    }
}
