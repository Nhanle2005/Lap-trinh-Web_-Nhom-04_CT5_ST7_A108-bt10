package nhanle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import nhanle.interceptor.AuthInterceptor;

/**
 * Cấu hình Web MVC với Interceptor cho phân quyền
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") // Áp dụng cho tất cả routes
                .excludePathPatterns(
                    "/css/**",
                    "/js/**", 
                    "/images/**",
                    "/static/**",
                    "/favicon.ico",
                    "/error"
                ); // Loại trừ static resources và error page
    }
}