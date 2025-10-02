package nhanle.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nhanle.entity.Role;
import nhanle.entity.User;

/**
 * Interceptor để kiểm tra phân quyền truy cập
 * - Admin routes: /admin/**
 * - User routes: /user/**, /products, /categories (chỉ xem)
 * - Public routes: /, /login, /logout, /graphql, /graphiql
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        // Public routes - không cần kiểm tra
        if (isPublicRoute(requestURI)) {
            return true;
        }
        
        // Kiểm tra đăng nhập
        if (currentUser == null) {
            response.sendRedirect("/login?error=unauthorized");
            return false;
        }
        
        boolean isAdmin = currentUser.hasRole(Role.RoleName.ADMIN);
        
        // Admin routes - chỉ admin mới được truy cập
        if (isAdminRoute(requestURI)) {
            if (!isAdmin) {
                response.sendRedirect("/login?error=admin_only");
                return false;
            }
            return true;
        }
        
        // User routes - user và admin đều được truy cập
        if (isUserRoute(requestURI)) {
            return true;
        }
        
        // Default: cho phép truy cập
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
        // Thêm thông tin user vào model nếu có
        if (modelAndView != null) {
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("currentUser");
            
            if (currentUser != null) {
                modelAndView.addObject("currentUser", currentUser);
                modelAndView.addObject("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
                modelAndView.addObject("isLoggedIn", true);
            } else {
                modelAndView.addObject("isLoggedIn", false);
            }
        }
    }

    /**
     * Kiểm tra route công khai
     */
    private boolean isPublicRoute(String uri) {
        return uri.equals("/") || 
               uri.equals("/login") || 
               uri.equals("/logout") ||
               uri.startsWith("/css/") ||
               uri.startsWith("/js/") ||
               uri.startsWith("/images/") ||
               uri.startsWith("/static/") ||
               uri.equals("/graphql") ||
               uri.equals("/graphiql") ||
               uri.equals("/favicon.ico");
    }

    /**
     * Kiểm tra admin routes
     */
    private boolean isAdminRoute(String uri) {
        return uri.startsWith("/admin/") ||
               uri.equals("/admin-dashboard") ||
               uri.equals("/admin");
    }

    /**
     * Kiểm tra user routes (user và admin đều được truy cập để xem)
     */
    private boolean isUserRoute(String uri) {
        return uri.startsWith("/user/") ||
               uri.equals("/user-dashboard") ||
               uri.equals("/user") ||
               uri.equals("/products") ||
               uri.equals("/categories") ||
               uri.equals("/users") ||
               uri.equals("/products-by-price") ||
               uri.equals("/products-by-category");
    }

}