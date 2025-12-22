package observability.context;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import observability.repository.UserRepository;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private static final List<String> OPEN_ROUTES = List.of("/user/login", "/user/signup");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final UserRepository userRepository;

    @Autowired
    public UserInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.err.println("PreHandle");


        // Récupérer le header
        String userId = request.getHeader("X-User-Id");
        String requestUri = request.getRequestURI();
        //System.err.println(requestUri);
        //System.err.println(userId);



        // Est-ce une route "ouverte" ?
        boolean isOpenRoute = OPEN_ROUTES.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestUri));

        if (isOpenRoute) {
            System.err.println(isOpenRoute);

            // Si l'ID est là, on le prend, sinon on continue quand même.
            if (userId != null && !userId.isBlank()) {
                UserContext.setCurrentUser(userId);
                MDC.put("userId", userId);
            }
            return true; // On laisse toujours passer
        } else {

//            if (UserContext.getCurrentUser() == null) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Veuillez vous identifier d'abord");
//                System.err.println("Veuillez vous identifier d'abord");
//                return false;
//            }
            // C'est une route protégée. Pas d'ID = Pas d'entrée.
            if (userId == null || userId.isBlank()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Header X-User-Id manquant");
                return false;
            }

            if (!userRepository.existsById(userId)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "X-User-Id incorrect");
                return false;

            }
            UserContext.setCurrentUser(userId);
            return true;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
