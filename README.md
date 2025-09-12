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

### Test Accounts

| Username | Password | Description |
|----------|----------|-------------|
| admin | admin123 | System Administrator |
| user1 | password123 | Regular User |
| user2 | password123 | Regular User |
| test | test123 | Test User |

### Access Paths

- **Homepage**: `http://localhost:8080/webapp/`
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
│   │   └── DatabaseUtil.java         # Database Connection Utility
│   ├── servlet/
│   │   ├── LoginServlet.java         # Login Processing
│   │   └── WelcomeServlet.java       # Welcome Page
│   └── HelloServlet.java             # Example Servlet
├── src/main/webapp/
│   ├── WEB-INF/
│   │   └── web.xml                   # Web Configuration
│   ├── index.jsp                     # Homepage
│   ├── login.jsp                     # Login Page
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

### Adding New Features

1. Create new classes in appropriate packages
2. Update `web.xml` to configure new Servlet mappings
3. Create corresponding JSP pages
4. Test feature completeness

### Database Extension

1. Modify `User` model class
2. Update SQL statements in `UserDAO`
3. Execute database migration scripts

## 📄 License

This project is for learning and demonstration purposes only.

## 🤝 Contributing

Welcome to submit Issues and Pull Requests to improve this project!

---

**Note**: This is a demonstration project. Please implement security hardening including password encryption, SQL injection protection, etc. before using in production environment.
