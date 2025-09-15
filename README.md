# Java Web Application - User Login System

This is a web application developed using pure Java technology stack, implementing user login verification and personalized welcome page functionality.

## 🚀 Technology Stack

- **Java 11**
- **Maven** - Project Management
- **Servlet 4.0** - Web Services
- **JSP 2.3** - Page Templates
- **JSTL 1.2** - Tag Library
- **MySQL 8.0+** - Database
- **Tomcat 9.0** - Application Server

## 📋 Features

- ✅ User Login Verification
- ✅ User Registration System
- ✅ Secure Password Hashing (BCrypt)
- ✅ Password Strength Validation
- ✅ Database User Management
- ✅ Session Management
- ✅ Personalized Welcome Page
- ✅ Responsive UI Design
- ✅ Error Handling
- ✅ User Statistics

## 🛠️ Requirements

1. **Java 11** or higher
2. **Maven 3.6+**
3. **MySQL 8.0+**
4. **Tomcat 9.0+**

## 📦 Installation and Configuration

### 1. Database Configuration

1. Start MySQL service
2. Execute database initialization script:
   ```sql
   mysql -u root -p < database_init.sql
   ```
3. Modify database connection configuration (if needed):
   - File: `src/main/java/com/example/webapp/util/DatabaseUtil.java`
   - Modify database URL, username and password

### 2. Project Build

```bash
# Clean and compile project
mvn clean compile

# Package as WAR file
mvn clean package
```

### 3. Deploy to Tomcat

1. Copy the generated `target/webapp.war` file to Tomcat's `webapps` directory
2. Start Tomcat server
3. Access: `http://localhost:8080/webapp`

## 🎯 Usage Instructions

### User Registration

1. **Access Registration Page**: `http://localhost:8080/webapp/register`
2. **Fill Registration Form**:
   - Username (unique)
   - Email address
   - Full name
   - Password (must contain uppercase, lowercase, and numbers)
   - Confirm password
3. **Submit Registration**: System will hash password securely and create account

### Test Accounts

| Username  | Password        | Description |
|-----------|-----------------|-------------|
| hostedftp | Money123456789! | Test1       |
| admin     | Admin111111111@ | Test2       |
| user1     | Password123456# | Test3 (demo)|

**Note**: All test accounts need to be registered first using the registration form.

### Access Paths

- **Homepage**: `http://localhost:8080/webapp/`
- **Registration Page**: `http://localhost:8080/webapp/register`
- **Login Page**: `http://localhost:8080/webapp/login`
- **Welcome Page**: `http://localhost:8080/webapp/welcome`
- **Test Servlet**: `http://localhost:8080/webapp/hello`

## 📁 Project Structure

```
webapp/
├── src/main/java/com/example/webapp/
│   ├── model/
│   │   └── User.java                 # User Data Model
│   ├── dao/
│   │   └── UserDAO.java              # User Data Access Object
│   ├── util/
│   │   ├── DatabaseUtil.java         # Database Connection Utility
│   │   └── PasswordUtil.java         # Password Security Utility (BCrypt)
│   ├── servlet/
│   │   ├── LoginServlet.java         # Login Processing
│   │   ├── RegisterServlet.java      # User Registration
│   │   ├── WelcomeServlet.java       # Welcome Page
│   │   └── LogoutServlet.java        # Logout Processing
│   └── HelloServlet.java             # Example Servlet
├── src/main/webapp/
│   ├── WEB-INF/
│   │   └── web.xml                   # Web Configuration
│   ├── index.jsp                     # Homepage
│   ├── login.jsp                     # Login Page
│   ├── register.jsp                  # Registration Page
│   ├── welcome.jsp                   # Welcome Page
│   └── error.jsp                     # Error Page
├── database_init.sql                 # Database Initialization Script
├── pom.xml                          # Maven Configuration
└── README.md                        # Project Documentation
```

## 🔧 Configuration

### Database Connection Configuration

Modify the following configuration in `DatabaseUtil.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/webapp_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = "root"; // Change to your MySQL password
```

### Password Security Configuration

The application uses BCrypt for password hashing with the following configuration in `PasswordUtil.java`:

```java
private static final int BCRYPT_ROUNDS = 12; // BCrypt strength level
```

**Password Requirements**:
- Minimum 6 characters
- Must contain uppercase letters
- Must contain lowercase letters  
- Must contain numbers

### Session Configuration

Configure session timeout in `web.xml`:

```xml
<session-config>
    <session-timeout>30</session-timeout> <!-- 30 minutes -->
</session-config>
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Check if MySQL service is running
   - Verify database connection configuration
   - Confirm database and tables are created

2. **Compilation Errors**
   - Check if Java version is 11+
   - Run `mvn clean compile` to recompile

3. **Deployment Failed**
   - Check Tomcat service status
   - Confirm WAR file integrity
   - Check Tomcat logs

### Log Viewing

- **Application Logs**: Check console output
- **Tomcat Logs**: `$TOMCAT_HOME/logs/`
- **MySQL Logs**: Check MySQL error logs

## 📝 Development Notes

### Security Features

**Password Security**:
- All passwords are hashed using BCrypt algorithm
- BCrypt rounds set to 12 for strong security
- Passwords are never stored in plain text
- Password strength validation on registration

**User Registration**:
- Username uniqueness validation
- Email format validation
- Password confirmation matching
- Secure password hashing before database storage

### Adding New Features

1. Create new classes in appropriate packages
2. Update `web.xml` to configure new Servlet mappings
3. Create corresponding JSP pages
4. Test feature completeness

### Database Extension

1. Modify `User` model class
2. Update SQL statements in `UserDAO`
3. Execute database migration scripts

### Security Best Practices

1. **Password Handling**: Always use `PasswordUtil` for password operations
2. **Input Validation**: Validate all user inputs on both client and server side
3. **SQL Injection**: Use PreparedStatement for all database queries
4. **Session Management**: Implement proper session timeout and invalidation

## 📄 License

This project is for learning and demonstration purposes only.

## 🤝 Contributing

Welcome to submit Issues and Pull Requests to improve this project!

---

## 🔒 Security Implementation

This project now includes comprehensive security features:

- ✅ **Password Hashing**: BCrypt algorithm with 12 rounds
- ✅ **Password Strength Validation**: Enforces strong password requirements
- ✅ **SQL Injection Protection**: All queries use PreparedStatement
- ✅ **Input Validation**: Server-side validation for all user inputs
- ✅ **Session Security**: Proper session management and timeout
- ✅ **User Registration**: Secure account creation with validation

**Note**: This is a demonstration project with security features implemented. For production use, consider additional security measures such as HTTPS, rate limiting, and comprehensive logging.
