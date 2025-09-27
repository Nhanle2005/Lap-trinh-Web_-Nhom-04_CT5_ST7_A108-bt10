package nhanle.repository;

import nhanle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByPhone(String phone);
    
    @Query("SELECT u FROM User u WHERE u.fullname LIKE %:name%")
    List<User> findByFullnameContaining(String name);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.categories")
    List<User> findAllWithCategories();
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.products")
    List<User> findAllWithProducts();
}