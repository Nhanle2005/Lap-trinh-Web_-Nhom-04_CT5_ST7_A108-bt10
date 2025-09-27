package nhanle.resolver;

import nhanle.entity.Category;
import nhanle.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class CategoryResolver {
    
    @Autowired
    private CategoryService categoryService;
    
    // Query Resolvers
    @QueryMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
    @QueryMapping
    public Category getCategoryById(@Argument Long id) {
        return categoryService.getCategoryById(id).orElse(null);
    }
    
    @QueryMapping
    public Category getCategoryByName(@Argument String name) {
        return categoryService.getCategoryByName(name).orElse(null);
    }
    
    @QueryMapping
    public List<Category> searchCategoriesByName(@Argument String name) {
        return categoryService.searchCategoriesByName(name);
    }
    
    // Mutation Resolvers
    @MutationMapping
    public Category createCategory(@Argument Map<String, Object> input) {
        String name = (String) input.get("name");
        String images = (String) input.get("images");
        
        Category category = new Category(name, images);
        return categoryService.createCategory(category);
    }
    
    @MutationMapping
    public Category updateCategory(@Argument Map<String, Object> input) {
        Long id = Long.valueOf(input.get("id").toString());
        String name = (String) input.get("name");
        String images = (String) input.get("images");
        
        Category categoryDetails = new Category();
        if (name != null) categoryDetails.setName(name);
        if (images != null) categoryDetails.setImages(images);
        
        return categoryService.updateCategory(id, categoryDetails);
    }
    
    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        return categoryService.deleteCategory(id);
    }
}