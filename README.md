# 🚀 Product Management System - GraphQL + Spring Boot 3
### 1. Clone và chạy dự án

```bash
# 1. Điều hướng đến thư mục dự án
cd d:\wb1\demoSpringbootCT5ST7-3

# 2. Compile dự án
mvn clean compile

# 3. Chạy ứng dụng
mvn spring-boot:run
```

### 2. Kiểm tra khởi động thành công
Các URL để test

Trang chủ | http://localhost:8080/ | Dashboard chính |
Quản lý Sản phẩm | http://localhost:8080/products | CRUD sản phẩm |
Quản lý Danh mục | http://localhost:8080/categories | CRUD danh mục |
Quản lý Người dùng | http://localhost:8080/users | CRUD người dùng |
Sản phẩm theo giá | http://localhost:8080/products-by-price | Sắp xếp theo giá |
Sản phẩm theo danh mục | http://localhost:8080/products-by-category | Lọc theo danh mục |
| GraphQL API | http://localhost:8080/graphql | POST endpoint |

## Test GraphQL Queries

### Lấy sản phẩm theo giá (thấp → cao)
```graphql
query {
  getAllProductsByPriceAsc {
    id
    title
    price
    quantity
    category { name }
    user { fullname }
  }
}
```

### Lấy sản phẩm theo danh mục
```graphql
query {
  getProductsByCategory(categoryId: "1") {
    id
    title
    price
    desc
    user { fullname }
  }
}
```

### Tạo sản phẩm mới
```graphql
mutation {
  createProduct(input: {
    title: "Sản phẩm mới"
    quantity: 10
    desc: "Mô tả sản phẩm"
    price: 500000
    userId: "1"
    categoryId: "1"
  }) {
    id
    title
    price
  }
}
```

### Lấy tất cả danh mục
```graphql
query {
  getAllCategories {
    id
    name
    images
  }
}
```

## Dữ liệu mẫu

Hệ thống tự động khởi tạo dữ liệu mẫu:

### **Categories**
- Electronics (Điện tử)
- Clothing (Thời trang)  
- Books (Sách)
- Sports (Thể thao)

### **Users**
- Nguyen Van A (a@example.com)
- Tran Thi B (b@example.com)
- Le Van C (c@example.com)

### **Products** 
- **Electronics**: Laptop Dell, iPhone 15 Pro, Samsung Galaxy S24...
- **Clothing**: Nike Air Max, Adidas T-Shirt, Levi's Jeans...
- **Books**: Spring Boot in Action, Clean Code...
- **Sports**: Tennis Racket, Football, Yoga Mat...

