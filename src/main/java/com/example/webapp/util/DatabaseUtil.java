package com.example.webapp.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database Connection Utility Class
 * Provides MySQL database connection management
 * Supports both local and AWS RDS configurations
 */
public class DatabaseUtil {
    
    // Database connection configuration - loaded from properties file
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    private static String DB_DRIVER;
    
    // Static block to load database configuration and driver
    static {
        try {
            // Load properties file based on environment
            Properties props = new Properties();
            String configFile = getConfigFileName();
            
            System.out.println("Loading database configuration from: " + configFile);
            
            InputStream input = DatabaseUtil.class.getClassLoader()
                .getResourceAsStream(configFile);
            
            if (input == null) {
                throw new RuntimeException("Database configuration file not found: " + configFile);
            }
            
            props.load(input);
            input.close();
            
            // Load configuration properties
            DB_URL = props.getProperty("db.url");
            DB_USERNAME = props.getProperty("db.username");
            DB_PASSWORD = props.getProperty("db.password");
            DB_DRIVER = props.getProperty("db.driver");
            
            // Validate required properties
            if (DB_URL == null || DB_USERNAME == null || DB_PASSWORD == null || DB_DRIVER == null) {
                throw new RuntimeException("Missing required database configuration properties");
            }
            
            // Load database driver
            Class.forName(DB_DRIVER);
            System.out.println("Database configuration loaded successfully");
            
        } catch (Exception e) {
            System.err.println("Database configuration loading failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database configuration loading failed", e);
        }
    }
    
    /**
     * Get configuration file name based on environment
     * @return Configuration file name
     */
    private static String getConfigFileName() {
        // Check for AWS environment variable or system property
        String environment = System.getProperty("app.environment");
        if (environment == null) {
            environment = System.getenv("APP_ENVIRONMENT");
        }
        
        if ("aws".equalsIgnoreCase(environment)) {
            return "database-aws.properties";
        } else {
            return "database.properties"; // Default to local configuration
        }
    }
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException Database connection exception
     */
    public static Connection getConnection() throws SQLException {
        try {
            System.out.println("Attempting to connect to database: " + DB_URL);
            System.out.println("Database username: " + DB_USERNAME);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Database connection successful!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Close database connection
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error occurred while closing database connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test database connection
     * @return Whether connection is successful
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}
