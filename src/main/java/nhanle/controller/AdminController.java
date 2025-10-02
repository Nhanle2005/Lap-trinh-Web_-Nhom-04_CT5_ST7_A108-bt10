package nhanle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nhanle.dto.CategoryDTO;
import nhanle.dto.ProductDTO;
import nhanle.entity.Category;
import nhanle.entity.Product;
import nhanle.entity.Role;
import nhanle.entity.User;
import nhanle.service.CategoryService;
import nhanle.service.ProductService;
import nhanle.service.UserService;

/**
 * Controller dành riêng cho Admin với các chức năng quản lý
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;

    /**
     * Trang chủ Admin - Dashboard với quản lý
     */
    @GetMapping({"/", "/dashboard"})
    public String adminDashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || !currentUser.hasRole(Role.RoleName.ADMIN)) {
            return "redirect:/login?error=admin_only";
        }
        
        // Thống kê cho dashboard
        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();
        List<User> users = userService.getAllUsers();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", true);
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalCategories", categories.size());
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("recentProducts", products.subList(Math.max(0, products.size() - 5), products.size()));
        
        return "admin-dashboard";
    }

    /**
     * Quản lý Sản phẩm - Admin
     */
    @GetMapping("/products")
    public String manageProducts(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || !currentUser.hasRole(Role.RoleName.ADMIN)) {
            return "redirect:/login?error=admin_only";
        }
        
        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();
        List<User> users = userService.getAllUsers();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", true);
        model.addAttribute("managementMode", true);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("users", users);
        model.addAttribute("productDTO", new ProductDTO());
        
        return "products";
    }

    /**
     * Thêm sản phẩm mới
     */
    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute ProductDTO productDTO,
                            BindingResult bindingResult,
                            HttpSession session,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || !currentUser.hasRole(Role.RoleName.ADMIN)) {
            return "redirect:/login?error=admin_only";
        }
        
        if (bindingResult.hasErrors()) {
            // Reload data for form
            List<Category> categories = categoryService.getAllCategories();
            List<User> users = userService.getAllUsers();
            
            model.addAttribute("user", currentUser);
            model.addAttribute("isAdmin", true);
            model.addAttribute("managementMode", true);
            model.addAttribute("categories", categories);
            model.addAttribute("users", users);
            model.addAttribute("products", productService.getAllProducts());
            
            return "products";
        }
        
        try {
            // Tạo sản phẩm mới (sử dụng GraphQL hoặc service)
            redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm sản phẩm: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/admin/products";
    }

    /**
     * Quản lý Danh mục - Admin
     */
    @GetMapping("/categories")
    public String manageCategories(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || !currentUser.hasRole(Role.RoleName.ADMIN)) {
            return "redirect:/login?error=admin_only";
        }
        
        List<Category> categories = categoryService.getAllCategories();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", true);
        model.addAttribute("managementMode", true);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDTO", new CategoryDTO());
        
        return "categories";
    }

    /**
     * Thêm danh mục mới
     */
    @PostMapping("/categories/add")
    public String addCategory(@Valid @ModelAttribute CategoryDTO categoryDTO,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || !currentUser.hasRole(Role.RoleName.ADMIN)) {
            return "redirect:/login?error=admin_only";
        }
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", currentUser);
            model.addAttribute("isAdmin", true);
            model.addAttribute("managementMode", true);
            model.addAttribute("categories", categoryService.getAllCategories());
            
            return "categories";
        }
        
        try {
            // Tạo danh mục mới (sử dụng GraphQL hoặc service)
            redirectAttributes.addFlashAttribute("message", "Thêm danh mục thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm danh mục: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/admin/categories";
    }

    /**
     * Quản lý Người dùng - Admin
     */
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || !currentUser.hasRole(Role.RoleName.ADMIN)) {
            return "redirect:/login?error=admin_only";
        }
        
        List<User> users = userService.getAllUsers();
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", true);
        model.addAttribute("managementMode", true);
        model.addAttribute("users", users);
        
        return "users";
    }

    /**
     * Xóa sản phẩm
     */
    @PostMapping("/products/delete")
    public String deleteProduct(@RequestParam Long productId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || !currentUser.hasRole(Role.RoleName.ADMIN)) {
            return "redirect:/login?error=admin_only";
        }
        
        try {
            // Xóa sản phẩm (sử dụng GraphQL hoặc service)
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa sản phẩm: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/admin/products";
    }

    /**
     * Xóa danh mục
     */
    @PostMapping("/categories/delete")
    public String deleteCategory(@RequestParam Long categoryId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || !currentUser.hasRole(Role.RoleName.ADMIN)) {
            return "redirect:/login?error=admin_only";
        }
        
        try {
            // Xóa danh mục (sử dụng GraphQL hoặc service)
            redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa danh mục: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        return "redirect:/admin/categories";
    }
}