package nhanle.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhanle.entity.Category;
import nhanle.entity.User;
import nhanle.repository.CategoryRepository;
import nhanle.repository.UserRepository;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // Get user by phone
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
    
    // Update user
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setFullname(userDetails.getFullname());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setPhone(userDetails.getPhone());
        
        return userRepository.save(user);
    }
    
    // Delete user
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Search users by name
    public List<User> searchUsersByName(String name) {
        return userRepository.findByFullnameContaining(name);
    }
    
    // Add category to user (many-to-many relationship)
    public User addCategoryToUser(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        
        // Ensure categories collection is initialized
        if (user.getCategories() == null) {
            user.setCategories(new HashSet<>());
        }
        user.getCategories().add(category);
        return userRepository.save(user);
    }
    
    // Remove category from user
    public User removeCategoryFromUser(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        
        user.getCategories().remove(category);
        return userRepository.save(user);
    }
    
    // Get all users with their categories
    public List<User> getAllUsersWithCategories() {
        return userRepository.findAllWithCategories();
    }
    
    // Get all users with their products
    public List<User> getAllUsersWithProducts() {
        return userRepository.findAllWithProducts();
    }
}