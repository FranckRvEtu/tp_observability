package observability.context;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

public class UserInterceptor implements HandlerInterceptor {

    private static final List<String> OPEN_ROUTES = List.of("user/login", "user/signup");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.err.println("PreHandle");


        // 1. Récupérer le header
        String userId = request.getHeader("X-User-Id");
        String requestUri = request.getRequestURI();
        System.err.println(requestUri);
        System.err.println(userId);



        // 2. Est-ce une route "ouverte" ?
        boolean isOpenRoute = OPEN_ROUTES.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestUri));

        if (isOpenRoute) {
            System.err.println(isOpenRoute);
            // --- MODE RELAX ---
            // Si l'ID est là, on le prend, sinon on continue quand même.
            if (userId != null && !userId.isBlank()) {
                UserContext.setCurrentUser(userId);
                MDC.put("userId", userId);
            }
            return true; // On laisse toujours passer
        } else {
            // --- MODE STRICT (Vérification) ---
            // C'est une route protégée. Pas d'ID = Pas d'entrée.
            if (userId == null || userId.isBlank()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Header X-User-Id manquant");
                return false; // On bloque la requête ici ! Le controller ne sera pas appelé.
            }

            // Si tout va bien, on stocke et on passe
            UserContext.setCurrentUser(userId);
            return true;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
        MDC.clear();
    }}
