package com.example.webapp.servlet;

import com.example.webapp.dao.UserDAO;
import com.example.webapp.model.User;
import com.example.webapp.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User Registration Servlet
 * Handles new user registration with password security
 */
public class RegisterServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Pass password requirements to JSP
        request.setAttribute("passwordRequirements", PasswordUtil.getPasswordRequirements());
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // Get parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        
        // Validate input
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required!");
            request.setAttribute("passwordRequirements", PasswordUtil.getPasswordRequirements());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Check password confirmation
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match!");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("passwordRequirements", PasswordUtil.getPasswordRequirements());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        // Check password strength
        if (!PasswordUtil.isPasswordStrong(password)) {
            request.setAttribute("errorMessage", PasswordUtil.getPasswordRequirements() + "!");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("passwordRequirements", PasswordUtil.getPasswordRequirements());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        try {
            // Check if username already exists
            if (userDAO.findByUsername(username.trim()) != null) {
                request.setAttribute("errorMessage", "Username already exists!");
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                request.setAttribute("fullName", fullName);
                request.setAttribute("passwordRequirements", PasswordUtil.getPasswordRequirements());
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
            
            // Create new user with hashed password
            User newUser = new User();
            newUser.setUsername(username.trim());
            newUser.setPassword(PasswordUtil.hashPassword(password)); // Hash password
            newUser.setEmail(email.trim());
            newUser.setFullName(fullName.trim());
            
            // Save user to database
            if (userDAO.createUser(newUser)) {
                request.setAttribute("successMessage", "Registration successful! Please login with your credentials.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed! Please try again.");
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                request.setAttribute("fullName", fullName);
                request.setAttribute("passwordRequirements", PasswordUtil.getPasswordRequirements());
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during registration, please try again later!");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("passwordRequirements", PasswordUtil.getPasswordRequirements());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
