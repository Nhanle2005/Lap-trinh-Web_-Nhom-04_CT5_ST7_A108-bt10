package nhanle.resolver;

import nhanle.entity.User;
import nhanle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class UserResolver {
    
    @Autowired
    private UserService userService;
    
    // Query Resolvers
    @QueryMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @QueryMapping
    public User getUserById(@Argument Long id) {
        return userService.getUserById(id).orElse(null);
    }
    
    @QueryMapping
    public User getUserByEmail(@Argument String email) {
        return userService.getUserByEmail(email).orElse(null);
    }
    
    @QueryMapping
    public List<User> searchUsersByName(@Argument String name) {
        return userService.searchUsersByName(name);
    }
    
    // Mutation Resolvers
    @MutationMapping
    public User createUser(@Argument Map<String, Object> input) {
        String fullname = (String) input.get("fullname");
        String email = (String) input.get("email");
        String password = (String) input.get("password");
        String phone = (String) input.get("phone");
        
        User user = new User(fullname, email, password, phone);
        return userService.createUser(user);
    }
    
    @MutationMapping
    public User updateUser(@Argument Map<String, Object> input) {
        Long id = Long.valueOf(input.get("id").toString());
        String fullname = (String) input.get("fullname");
        String email = (String) input.get("email");
        String password = (String) input.get("password");
        String phone = (String) input.get("phone");
        
        User userDetails = new User();
        if (fullname != null) userDetails.setFullname(fullname);
        if (email != null) userDetails.setEmail(email);
        if (password != null) userDetails.setPassword(password);
        if (phone != null) userDetails.setPhone(phone);
        
        return userService.updateUser(id, userDetails);
    }
    
    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }
    
    @MutationMapping
    public User addCategoryToUser(@Argument Long userId, @Argument Long categoryId) {
        return userService.addCategoryToUser(userId, categoryId);
    }
    
    @MutationMapping
    public User removeCategoryFromUser(@Argument Long userId, @Argument Long categoryId) {
        return userService.removeCategoryFromUser(userId, categoryId);
    }
}