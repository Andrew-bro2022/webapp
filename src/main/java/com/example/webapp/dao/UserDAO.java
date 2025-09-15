package com.example.webapp.dao;

import com.example.webapp.model.User;
import com.example.webapp.util.DatabaseUtil;
import com.example.webapp.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User Data Access Object
 * Handles user-related database operations
 */
public class UserDAO {
    
    /**
     * Find user by username
     * @param username Username
     * @return User object, null if not found
     */
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, email, full_name, created_at FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while finding user: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Validate user login with secure password verification
     * @param username Username
     * @param password Plain text password
     * @return User object, null if validation fails
     */
    public User validateLogin(String username, String password) {
        String sql = "SELECT id, username, password, email, full_name, created_at FROM users WHERE username = ?";
        
        System.out.println("Executing SQL query: " + sql);
        System.out.println("Query parameters - Username: '" + username + "'");
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultSetToUser(rs);
                    String storedPassword = user.getPassword();
                    
                    // Use secure password verification
                    if (PasswordUtil.verifyPassword(password, storedPassword)) {
                        System.out.println("Database query successful, user found: " + user.getUsername());
                        // Clear password from user object before returning
                        user.setPassword(null);
                        return user;
                    } else {
                        System.out.println("Password verification failed for user: " + username);
                    }
                } else {
                    System.out.println("Database query completed, no matching user found");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred during login validation: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Create new user
     * @param user User object
     * @return Whether creation was successful
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, password, email, full_name) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFullName());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while creating user: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get all users
     * @return List of users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, email, full_name, created_at FROM users ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while getting user list: " + e.getMessage());
        }
        
        return users;
    }
    
    /**
     * Find user by ID
     * @param id User ID
     * @return User object, null if not found
     */
    public User findById(int id) {
        String sql = "SELECT id, username, password, email, full_name, created_at FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while finding user by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Map ResultSet to User object
     * @param rs ResultSet object
     * @return User object
     * @throws SQLException SQL exception
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setFullName(rs.getString("full_name"));
        
        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            user.setCreatedAt(timestamp.toLocalDateTime());
        }
        
        return user;
    }
}
