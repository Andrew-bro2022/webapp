-- =============================================
-- Java Web Application Database Initialization Script
-- For MySQL 8.0+
-- =============================================

-- Create database
CREATE DATABASE IF NOT EXISTS webapp_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Use database
USE webapp_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'User ID',
    username VARCHAR(50) UNIQUE NOT NULL COMMENT 'Username',
    password VARCHAR(255) NOT NULL COMMENT 'Password',
    email VARCHAR(100) COMMENT 'Email',
    full_name VARCHAR(100) COMMENT 'Full Name',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Created At',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated At'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Users Table';

-- Insert test data (COMMENTED OUT - Use registration feature instead)
-- INSERT INTO users (username, password, email, full_name) VALUES
-- ('admin', 'admin123', 'admin@example.com', 'System Administrator'),
-- ('user1', 'password123', 'user1@example.com', 'John Smith'),
-- ('user2', 'password123', 'user2@example.com', 'Jane Doe'),
-- ('test', 'test123', 'test@example.com', 'Test User'),
-- ('hostedftp', 'money', 'hostedftp@example.com', 'HostedFTP User');

-- Create user sessions table (optional, for session management)
CREATE TABLE IF NOT EXISTS user_sessions (
    id VARCHAR(255) PRIMARY KEY COMMENT 'Session ID',
    user_id INT NOT NULL COMMENT 'User ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Created At',
    last_accessed TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last Accessed',
    ip_address VARCHAR(45) COMMENT 'IP Address',
    user_agent TEXT COMMENT 'User Agent',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='User Sessions Table';

-- Create user activities log table (optional, for recording user activities)
CREATE TABLE IF NOT EXISTS user_activities (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Activity ID',
    user_id INT NOT NULL COMMENT 'User ID',
    activity_type VARCHAR(50) NOT NULL COMMENT 'Activity Type',
    activity_description TEXT COMMENT 'Activity Description',
    ip_address VARCHAR(45) COMMENT 'IP Address',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Created At',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='User Activities Log Table';

-- Insert some sample activity logs (COMMENTED OUT - No users exist yet)
-- INSERT INTO user_activities (user_id, activity_type, activity_description, ip_address) VALUES
-- (1, 'LOGIN', 'User logged into system', '127.0.0.1'),
-- (1, 'VIEW_WELCOME', 'Viewed welcome page', '127.0.0.1'),
-- (2, 'LOGIN', 'User logged into system', '192.168.1.100'),
-- (3, 'LOGIN', 'User logged into system', '192.168.1.101');

-- Create indexes to improve query performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_sessions_user_id ON user_sessions(user_id);
CREATE INDEX idx_activities_user_id ON user_activities(user_id);
CREATE INDEX idx_activities_created_at ON user_activities(created_at);

-- Show created tables
SHOW TABLES;

-- Show users table structure
DESCRIBE users;

-- Show inserted test data (COMMENTED OUT - No test data inserted)
-- SELECT * FROM users;

-- Show statistics
SELECT 
    'Total Users' as Statistic,
    COUNT(*) as Count
FROM users
UNION ALL
SELECT 
    'Total Activities' as Statistic,
    COUNT(*) as Count
FROM user_activities;

-- =============================================
-- Usage Instructions
-- =============================================
-- 1. Ensure MySQL service is running
-- 2. Execute this script with root user or user with sufficient privileges
-- 3. Default database connection configuration:
--    - Host: localhost
--    - Port: 3306
--    - Database: webapp_db
--    - Username: root
--    - Password: root (modify as needed)
-- 4. User Registration:
--    - Use the registration feature to create new users
--    - Passwords are securely hashed using BCrypt
--    - Test account: Username: hostedftp, Password: money (create via registration)
-- =============================================


-- =============================================
-- AWS RDS Configuration Notes
-- =============================================
-- 1. RDS endpoint: your-rds-endpoint.amazonaws.com
-- 2. Port: 3306 (default)
-- 3. SSL: Required (useSSL=true)
-- 4. Timezone: UTC
-- 5. Character set: utf8mb4
-- =============================================
