# DEMO JWT AUTHENTICATION FULLSTACK
# Tạo cơ sở dữ liệu (Option 1)
- Tạo một cơ sở dữ liệu có tên là: SecurityJwt
- Kiểm tra cấu hình của cơ sở dữ liệu trong project spring

# Chạy file database cho sẵn (Option 2)
- Chạy file có tên là: database.sql, để có vài dữ liệu có sẵn
- Kiểm tra cấu hình của cơ sở dữ liệu trong project spring

# Có thể test bằng:
1. Web browser
2. Postman

# Luồng cơ bản của jwt authentication

    Login -> Validate username and password -> Create jwt token 
    -> Send to API -> Store token to localstorage

    Get token from localstorage -> API send token to server
    -> Server extract token to get user'data -> Send user's data to API
   