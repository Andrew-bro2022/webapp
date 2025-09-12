package com.example.webapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout Servlet
 * Handles user logout and session cleanup
 */
public class LogoutServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init() throws ServletException {
        System.out.println("LogoutServlet initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("=== Logout Debug Info ===");
        
        // Get current session
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            String username = (String) session.getAttribute("username");
            System.out.println("Logging out user: " + username);
            
            // Invalidate the session
            session.invalidate();
            System.out.println("Session invalidated successfully");
        } else {
            System.out.println("No active session found");
        }
        
        // Redirect to login page
        System.out.println("Redirecting to login page");
        response.sendRedirect("login");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect to GET method
        doGet(request, response);
    }
}
