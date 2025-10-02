package nhanle.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    
    private Long id;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 200, message = "Tên sản phẩm phải từ 2-200 ký tự")
    private String title;
    
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải >= 0")
    @Max(value = 99999, message = "Số lượng không được quá 99999")
    private Integer quantity;
    
    @Size(max = 1000, message = "Mô tả không được quá 1000 ký tự")
    private String desc;
    
    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải > 0")
    @DecimalMax(value = "999999999.99", message = "Giá không được quá 999,999,999.99")
    @Digits(integer = 9, fraction = 2, message = "Giá phải có tối đa 9 số nguyên và 2 số thập phân")
    private BigDecimal price;
    
    @NotNull(message = "Người dùng không được để trống")
    private Long userId;
    
    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;
    
    // Constructor không bao gồm ID (cho create)
    public ProductDTO(String title, Integer quantity, String desc, BigDecimal price, Long userId, Long categoryId) {
        this.title = title;
        this.quantity = quantity;
        this.desc = desc;
        this.price = price;
        this.userId = userId;
        this.categoryId = categoryId;
    }
}