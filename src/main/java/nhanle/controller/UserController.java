package nhanle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import nhanle.entity.Category;
import nhanle.entity.Product;
import nhanle.entity.Role;
import nhanle.entity.User;
import nhanle.service.CategoryService;
import nhanle.service.ProductService;
import nhanle.service.UserService;

/**
 * Controller dành cho User - chỉ xem, không được quản lý
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;

    /**
     * Trang chủ User - Dashboard chỉ xem
     */
    @GetMapping({"/", "/dashboard"})
    public String userDashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        // Thống kê cho dashboard (chỉ xem)
        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalCategories", categories.size());
        model.addAttribute("recentProducts", products.subList(Math.max(0, products.size() - 10), products.size()));
        model.addAttribute("viewMode", true); // Chế độ chỉ xem
        
        return "user-dashboard";
    }

    /**
     * Xem Sản phẩm - User (chỉ đọc)
     */
    @GetMapping("/products")
    public String viewProducts(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("viewMode", true); // Chế độ chỉ xem
        
        return "products";
    }

    /**
     * Xem Danh mục - User (chỉ đọc)
     */
    @GetMapping("/categories")
    public String viewCategories(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        List<Category> categories = categoryService.getAllCategories();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        model.addAttribute("categories", categories);
        model.addAttribute("viewMode", true); // Chế độ chỉ xem
        
        return "categories";
    }

    /**
     * Xem Người dùng - User (chỉ đọc)
     */
    @GetMapping("/users")
    public String viewUsers(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        List<User> users = userService.getAllUsers();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        model.addAttribute("users", users);
        model.addAttribute("viewMode", true); // Chế độ chỉ xem
        
        return "users";
    }

    /**
     * Xem sản phẩm theo giá - User
     */
    @GetMapping("/products-by-price")
    public String viewProductsByPrice(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        model.addAttribute("viewMode", true);
        
        return "products-by-price";
    }

    /**
     * Xem sản phẩm theo danh mục - User
     */
    @GetMapping("/products-by-category")
    public String viewProductsByCategory(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        model.addAttribute("viewMode", true);
        
        return "products-by-category";
    }
}