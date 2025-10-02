package nhanle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import nhanle.entity.Role;
import nhanle.entity.User;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/products")
    public String products(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
            model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        }
        return "products";
    }
    
    @GetMapping("/categories")
    public String categories(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
            model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        }
        return "categories";
    }
    
    @GetMapping("/users")
    public String users(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        // Chỉ cho phép admin và user đã đăng nhập xem danh sách users
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        return "users";
    }
    
    @GetMapping("/products-by-price")
    public String productsByPrice(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
            model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        }
        return "products-by-price";
    }
    
    @GetMapping("/products-by-category")
    public String productsByCategory(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
            model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        }
        return "products-by-category";
    }
}