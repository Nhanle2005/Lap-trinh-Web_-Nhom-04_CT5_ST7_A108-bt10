package nhanle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/products")
    public String products() {
        return "products";
    }
    
    @GetMapping("/categories")
    public String categories() {
        return "categories";
    }
    
    @GetMapping("/users")
    public String users() {
        return "users";
    }
    
    @GetMapping("/products-by-price")
    public String productsByPrice() {
        return "products-by-price";
    }
    
    @GetMapping("/products-by-category")
    public String productsByCategory() {
        return "products-by-category";
    }
}