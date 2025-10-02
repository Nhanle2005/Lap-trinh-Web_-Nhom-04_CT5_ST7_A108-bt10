package nhanle.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nhanle.dto.LoginDTO;
import nhanle.entity.Role;
import nhanle.entity.User;
import nhanle.service.UserService;

/**
 * Controller for handling authentication-related requests
 * This is a simple authentication implementation without Spring Security
 * for testing purposes while security components are in backup folder
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * Show login page
     */
    @GetMapping("/login")
    public String showLoginPage(Model model, 
                               @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               HttpSession session) {
        
        // Check if user is already logged in and redirect accordingly
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            boolean isAdmin = currentUser.hasRole(Role.RoleName.ADMIN);
            if (isAdmin) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        }
        
        // Add LoginDTO to model for form binding
        model.addAttribute("loginDTO", new LoginDTO());
        
        // Handle error messages
        if (error != null) {
            switch (error) {
                case "unauthorized":
                    model.addAttribute("error", "Bạn cần đăng nhập để truy cập trang này!");
                    break;
                case "access_denied":
                    model.addAttribute("error", "Bạn không có quyền truy cập trang này!");
                    break;
                case "admin_only":
                    model.addAttribute("error", "Chỉ có Admin mới được phép truy cập trang này!");
                    break;
                default:
                    model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
                    break;
            }
        }
        
        // Handle logout message
        if (logout != null) {
            model.addAttribute("message", "Đăng xuất thành công!");
        }
        
        return "login";
    }

    /**
     * Process login form (Simple authentication without Spring Security)
     */
    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute LoginDTO loginDTO,
                              BindingResult bindingResult,
                              @RequestParam(value = "remember-me", required = false) String rememberMe,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        
        // Kiểm tra validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginDTO", loginDTO);
            return "login";
        }
        
        try {
            // Find user by email
            Optional<User> userOptional = userService.getUserByEmail(loginDTO.getEmail());
            
            if (userOptional.isPresent() && loginDTO.getPassword().equals("password123")) { // Simple password check
                User user = userOptional.get();
                // Set user session
                session.setAttribute("currentUser", user);
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userRoles", user.getRoles());
                
                // Check user role and redirect accordingly
                boolean isAdmin = user.hasRole(Role.RoleName.ADMIN);
                
                if (isAdmin) {
                    redirectAttributes.addFlashAttribute("message", 
                        "Chào mừng Admin " + user.getFullname() + "!");
                    return "redirect:/admin/dashboard";  // Trang chủ admin với quản lý
                } else {
                    redirectAttributes.addFlashAttribute("message", 
                        "Chào mừng " + user.getFullname() + "!");
                    return "redirect:/user/dashboard";   // Trang chủ user chỉ xem
                }
                
            } else {
                // Invalid credentials
                model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
                model.addAttribute("loginDTO", new LoginDTO());
                return "login";
            }
            
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi trong quá trình đăng nhập!");
            model.addAttribute("loginDTO", new LoginDTO());
            return "login";
        }
    }

    /**
     * Admin Dashboard
     */
    @GetMapping("/admin-dashboard")
    public String showAdminDashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        // Check if user has admin role
        boolean isAdmin = currentUser.hasRole(Role.RoleName.ADMIN);
            
        if (!isAdmin) {
            return "redirect:/login?error=admin_only";
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", true);
        return "admin-dashboard";
    }

    /**
     * User Dashboard  
     */
    @GetMapping("/user-dashboard")
    public String showUserDashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login?error=unauthorized";
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("isAdmin", currentUser.hasRole(Role.RoleName.ADMIN));
        return "user-dashboard";
    }

    /**
     * Logout
     */
    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // Clear session
        session.invalidate();
        
        redirectAttributes.addAttribute("logout", "true");
        return "redirect:/login";
    }
    
    /**
     * Logout via GET (for convenience)
     */
    @GetMapping("/logout")
    public String logoutGet(HttpSession session, RedirectAttributes redirectAttributes) {
        // Clear session
        session.invalidate();
        
        redirectAttributes.addAttribute("logout", "true");
        return "redirect:/login";
    }
}