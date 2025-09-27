package nhanle.resolver;

import nhanle.entity.Product;
import nhanle.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class ProductResolver {
    
    @Autowired
    private ProductService productService;
    
    // Query Resolvers
    @QueryMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @QueryMapping
    public Product getProductById(@Argument Long id) {
        return productService.getProductById(id).orElse(null);
    }
    
    // Main requirement: Get all products ordered by price (low to high)
    @QueryMapping
    public List<Product> getAllProductsByPriceAsc() {
        return productService.getAllProductsByPriceAsc();
    }
    
    // Main requirement: Get all products of a specific category
    @QueryMapping
    public List<Product> getProductsByCategory(@Argument Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
    
    @QueryMapping
    public List<Product> searchProductsByTitle(@Argument String title) {
        return productService.searchProductsByTitle(title);
    }
    
    @QueryMapping
    public List<Product> getProductsByPriceRange(@Argument BigDecimal minPrice, @Argument BigDecimal maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }
    
    @QueryMapping
    public List<Product> getProductsByUserId(@Argument Long userId) {
        return productService.getProductsByUserId(userId);
    }
    
    // Mutation Resolvers
    @MutationMapping
    public Product createProduct(@Argument Map<String, Object> input) {
        String title = (String) input.get("title");
        Integer quantity = Integer.valueOf(input.get("quantity").toString());
        String desc = (String) input.get("desc");
        BigDecimal price = new BigDecimal(input.get("price").toString());
        Long userId = Long.valueOf(input.get("userId").toString());
        Long categoryId = Long.valueOf(input.get("categoryId").toString());
        
        return productService.createProduct(title, quantity, desc, price, userId, categoryId);
    }
    
    @MutationMapping
    public Product updateProduct(@Argument Map<String, Object> input) {
        Long id = Long.valueOf(input.get("id").toString());
        
        Product productDetails = new Product();
        if (input.get("title") != null) {
            productDetails.setTitle((String) input.get("title"));
        }
        if (input.get("quantity") != null) {
            productDetails.setQuantity(Integer.valueOf(input.get("quantity").toString()));
        }
        if (input.get("desc") != null) {
            productDetails.setDesc((String) input.get("desc"));
        }
        if (input.get("price") != null) {
            productDetails.setPrice(new BigDecimal(input.get("price").toString()));
        }
        
        return productService.updateProduct(id, productDetails);
    }
    
    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }
}