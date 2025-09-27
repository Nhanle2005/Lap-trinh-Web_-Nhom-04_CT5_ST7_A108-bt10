package nhanle.service;

import nhanle.entity.Product;
import nhanle.entity.User;
import nhanle.entity.Category;
import nhanle.repository.ProductRepository;
import nhanle.repository.UserRepository;
import nhanle.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // Create a new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    // Create product with user ID and category ID
    public Product createProduct(String title, Integer quantity, String desc, 
                               BigDecimal price, Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        
        Product product = new Product(title, quantity, desc, price, user, category);
        return productRepository.save(product);
    }
    
    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // Get all products ordered by price (low to high)
    public List<Product> getAllProductsByPriceAsc() {
        return productRepository.findAllOrderByPriceAsc();
    }
    
    // Get all products of a specific category
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    // Get product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    // Update product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setTitle(productDetails.getTitle());
        product.setQuantity(productDetails.getQuantity());
        product.setDesc(productDetails.getDesc());
        product.setPrice(productDetails.getPrice());
        
        if (productDetails.getUser() != null) {
            product.setUser(productDetails.getUser());
        }
        if (productDetails.getCategory() != null) {
            product.setCategory(productDetails.getCategory());
        }
        
        return productRepository.save(product);
    }
    
    // Delete product
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Search products by title
    public List<Product> searchProductsByTitle(String title) {
        return productRepository.findByTitleContaining(title);
    }
    
    // Get products by price range
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    // Get products by user ID
    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }
    
    // Get products with quantity greater than specified value
    public List<Product> getProductsWithQuantityGreaterThan(Integer quantity) {
        return productRepository.findByQuantityGreaterThan(quantity);
    }
}