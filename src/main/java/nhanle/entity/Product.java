package nhanle.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String desc;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    // Many-to-one relationship with User (product belongs to a user)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Many-to-one relationship with Category (product belongs to a category)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    public Product(String title, Integer quantity, String desc, BigDecimal price, User user, Category category) {
        this.title = title;
        this.quantity = quantity;
        this.desc = desc;
        this.price = price;
        this.user = user;
        this.category = category;
    }
}