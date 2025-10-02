package nhanle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải từ 2-100 ký tự")
    private String fullname;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email không được quá 100 ký tự")
    private String email;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 255, message = "Mật khẩu phải từ 6-255 ký tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{6,}$", 
             message = "Mật khẩu phải có ít nhất 1 chữ hoa, 1 chữ thường, 1 số")
    private String password;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải có 10-11 số")
    private String phone;
    
    @NotBlank(message = "Role không được để trống")
    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role chỉ có thể là USER hoặc ADMIN")
    private String role = "USER";
    
    // Constructor cho login
    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Constructor cho register
    public UserDTO(String fullname, String email, String password, String phone, String role) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }
}