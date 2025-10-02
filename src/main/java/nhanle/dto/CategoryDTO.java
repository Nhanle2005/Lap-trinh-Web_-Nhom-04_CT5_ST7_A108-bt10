package nhanle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    
    private Long id;
    
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(min = 2, max = 100, message = "Tên danh mục phải từ 2-100 ký tự")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ\\s]+$", message = "Tên danh mục chỉ được chứa chữ cái và khoảng trắng")
    private String name;
    
    @Size(max = 255, message = "URL hình ảnh không được quá 255 ký tự")
    @Pattern(regexp = "^(https?:\\/\\/.*\\.(jpg|jpeg|png|gif|webp)|[a-zA-Z0-9_-]+\\.(jpg|jpeg|png|gif|webp))?$", 
             message = "Hình ảnh phải là URL hợp lệ hoặc tên file ảnh")
    private String images;
    
    public CategoryDTO(String name, String images) {
        this.name = name;
        this.images = images;
    }
}