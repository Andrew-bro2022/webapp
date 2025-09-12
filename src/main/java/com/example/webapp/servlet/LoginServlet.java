package com.example.webapp.servlet;

import com.example.webapp.dao.UserDAO;
import com.example.webapp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login Servlet
 * Handles user login validation and session management
 */
// @WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
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
        // If user is already logged in, redirect to welcome page
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("welcome");
            return;
        }
        
        // Display login page
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set request and response character encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // Get login parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validate input parameters
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password cannot be empty!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        try {
            // Add debug information
            System.out.println("=== Login Debug Info ===");
            System.out.println("Attempting login - Username: '" + username + "', Password: '" + password + "'");
            
            // Validate user login
            User user = userDAO.validateLogin(username.trim(), password);
            
            System.out.println("Login validation result: " + (user != null ? "Success" : "Failed"));
            if (user != null) {
                System.out.println("User found: " + user.getUsername() + " (ID: " + user.getId() + ")");
            }
            
            if (user != null) {
                // Login successful, create session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("loginTime", new java.util.Date());
                
                System.out.println("User login successful, redirecting to welcome page");
                
                // Redirect to welcome page
                response.sendRedirect("welcome");
            } else {
                // Login failed
                System.out.println("Login failed - Invalid username or password");
                request.setAttribute("errorMessage", "Invalid username or password!");
                request.setAttribute("username", username); // Keep username
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            // Handle exceptions
            System.err.println("Error occurred during login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during login, please try again later!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle logout request
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login");
    }
}
