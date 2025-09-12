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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Welcome Page Servlet
 * Displays welcome information and personalized data after user login
 */
// @WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    
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
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        
        try {
            // Get current logged in user
            User currentUser = (User) session.getAttribute("user");
            
            // Get latest user information from database
            User userFromDB = userDAO.findById(currentUser.getId());
            if (userFromDB != null) {
                // Update user information in session
                session.setAttribute("user", userFromDB);
                currentUser = userFromDB;
            }
            
            // Get user statistics
            List<User> allUsers = userDAO.getAllUsers();
            int totalUsers = allUsers.size();
            
            // Calculate days since registration
            long daysSinceRegistration = 0;
            if (currentUser.getCreatedAt() != null) {
                daysSinceRegistration = java.time.temporal.ChronoUnit.DAYS.between(
                    currentUser.getCreatedAt().toLocalDate(), 
                    LocalDateTime.now().toLocalDate()
                );
            }
            
            // Set request attributes
            request.setAttribute("user", currentUser);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("daysSinceRegistration", daysSinceRegistration);
            request.setAttribute("loginTime", session.getAttribute("loginTime"));
            request.setAttribute("currentTime", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            // Forward to welcome page
            request.getRequestDispatcher("/welcome.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error occurred while loading welcome page: " + e.getMessage());
            request.setAttribute("errorMessage", "An error occurred while loading user information, please try again later!");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect to GET method
        doGet(request, response);
    }
}
