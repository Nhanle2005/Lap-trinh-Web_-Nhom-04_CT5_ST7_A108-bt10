package nhanle.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import nhanle.entity.Category;
import nhanle.entity.Role;
import nhanle.entity.User;
import nhanle.service.CategoryService;
import nhanle.service.ProductService;
import nhanle.service.RoleService;
import nhanle.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private RoleService roleService;
    
    @Override
    public void run(String... args) throws Exception {
        // Create Roles
        roleService.findOrCreateRole(Role.RoleName.USER);
        roleService.findOrCreateRole(Role.RoleName.ADMIN);
        
        // Create Categories
        Category electronics = categoryService.createCategory(new Category("Electronics", "electronics.jpg"));
        Category clothing = categoryService.createCategory(new Category("Clothing", "clothing.jpg"));
        Category books = categoryService.createCategory(new Category("Books", "books.jpg"));
        Category sports = categoryService.createCategory(new Category("Sports", "sports.jpg"));
        
        // Create Users first without roles
        User user1 = userService.createUser(new User("Nguyen Van A", "a@example.com", "password123", "0901234567"));
        User user2 = userService.createUser(new User("Tran Thi B", "b@example.com", "password123", "0902345678"));
        User user3 = userService.createUser(new User("Le Van C", "c@example.com", "password123", "0903456789"));
        
        // Add roles to users
        // user1 (Nguyen Van A) has ADMIN role
        userService.addRoleToUser(user1.getId(), Role.RoleName.ADMIN);
        userService.addRoleToUser(user1.getId(), Role.RoleName.USER);
        
        // user2 and user3 have USER role
        userService.addRoleToUser(user2.getId(), Role.RoleName.USER);
        userService.addRoleToUser(user3.getId(), Role.RoleName.USER);
        
        // Add categories to users (many-to-many relationship)
        // Note: Removed for now to avoid ConcurrentModificationException during startup
        // These relationships can be added through the web interface
        
        // Create Products
        // Electronics products with varying prices
        productService.createProduct("Laptop Dell XPS 13", 10, "High-performance laptop", new BigDecimal("25000000"), user1.getId(), electronics.getId());
        productService.createProduct("iPhone 15 Pro", 15, "Latest iPhone model", new BigDecimal("30000000"), user1.getId(), electronics.getId());
        productService.createProduct("Samsung Galaxy S24", 8, "Android flagship phone", new BigDecimal("22000000"), user2.getId(), electronics.getId());
        productService.createProduct("iPad Air", 12, "Powerful tablet for work and play", new BigDecimal("18000000"), user1.getId(), electronics.getId());
        productService.createProduct("AirPods Pro", 25, "Wireless earbuds with noise cancellation", new BigDecimal("6000000"), user3.getId(), electronics.getId());
        
        // Clothing products
        productService.createProduct("Nike Air Max", 20, "Comfortable running shoes", new BigDecimal("3500000"), user2.getId(), clothing.getId());
        productService.createProduct("Adidas T-Shirt", 50, "Cotton t-shirt", new BigDecimal("500000"), user2.getId(), clothing.getId());
        productService.createProduct("Levi's Jeans", 15, "Classic denim jeans", new BigDecimal("1200000"), user2.getId(), clothing.getId());
        
        // Books
        productService.createProduct("Spring Boot in Action", 30, "Learn Spring Boot development", new BigDecimal("800000"), user1.getId(), books.getId());
        productService.createProduct("Clean Code", 25, "Writing better code", new BigDecimal("600000"), user1.getId(), books.getId());
        productService.createProduct("Java: The Complete Reference", 20, "Comprehensive Java guide", new BigDecimal("1000000"), user1.getId(), books.getId());
        
        // Sports products
        productService.createProduct("Wilson Tennis Racket", 5, "Professional tennis racket", new BigDecimal("2500000"), user2.getId(), sports.getId());
        productService.createProduct("Nike Football", 10, "Official size football", new BigDecimal("800000"), user1.getId(), sports.getId());
        productService.createProduct("Yoga Mat", 15, "Non-slip yoga mat", new BigDecimal("300000"), user3.getId(), sports.getId());
        
        // More products
        productService.createProduct("Design Patterns Book", 20, "Gang of Four design patterns", new BigDecimal("720000"), user2.getId(), books.getId());
        productService.createProduct("Algorithms Book", 18, "Introduction to Algorithms", new BigDecimal("950000"), user3.getId(), books.getId());
        
        System.out.println("Sample data initialized successfully!");
    }
}