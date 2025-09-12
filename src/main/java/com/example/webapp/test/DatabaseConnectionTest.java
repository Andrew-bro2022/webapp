package com.example.webapp.test;

import com.example.webapp.util.DatabaseUtil;

// /**
//  * Local Database Connection Test Class
//  * Used to verify database configuration is working correctly
//  */
// public class DatabaseConnectionTest {
    
//     public static void main(String[] args) {
//         System.out.println("=== Database Connection Test ===");
        
//         try {
//             // Test database connection
//             boolean isConnected = DatabaseUtil.testConnection();
            
//             if (isConnected) {
//                 System.out.println("✅ Database connection test PASSED");
//                 System.out.println("✅ Configuration loaded successfully");
//             } else {
//                 System.out.println("❌ Database connection test FAILED");
//             }
            
//         } catch (Exception e) {
//             System.err.println("❌ Database connection test FAILED with exception:");
//             e.printStackTrace();
//         }
        
//         System.out.println("=== Test Complete ===");
//     }
// }


//AWS Database Connection Test Class
public class DatabaseConnectionTest {
    public static void main(String[] args) {
        // set AWS environment
        System.setProperty("app.environment", "aws");
        
        System.out.println("=== Database Connection Test ===");
        System.out.println("Environment: " + System.getProperty("app.environment"));
        
        if (DatabaseUtil.testConnection()) {
            System.out.println("Database connection test successful!");
        } else {
            System.err.println("Database connection test failed!");
        }
        System.out.println("=== Test Complete ===");
    }
}

