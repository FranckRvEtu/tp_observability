package observability.config;

import observability.context.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).
                addPathPatterns("/**").
                excludePathPatterns(
                        "/",
                        "/login",
                        "/signup",
                        "/products",
                        "/products/add",
                        "/products/{id}",
                        "/stats",
                        "/user/signup",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/error",
                        "/products/{id}/edit"
                );
    }
}
