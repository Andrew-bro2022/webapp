package com.example.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple Hello World Servlet Example
 * Demonstrates basic Servlet functionality and response handling
 */
public class HelloServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type and encoding
        response.setContentType("text/html; charset=UTF-8");
        
        // Get request parameters
        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            name = "Guest";
        }
        
        // Get current time
        String currentTime = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Generate response HTML
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Hello Servlet</title>");
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f0f8ff; }");
        out.println("        .container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("        h1 { color: #333; text-align: center; }");
        out.println("        .info { background: #e8f4fd; padding: 15px; border-radius: 4px; margin: 20px 0; }");
        out.println("        .btn { display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; margin: 10px 5px; }");
        out.println("        .btn:hover { background-color: #0056b3; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='container'>");
        out.println("        <h1>👋 Hello, " + name + "!</h1>");
        out.println("        <div class='info'>");
        out.println("            <p><strong>Welcome to Java Web Application!</strong></p>");
        out.println("            <p><strong>Current Time:</strong> " + currentTime + "</p>");
        out.println("            <p><strong>Request Method:</strong> " + request.getMethod() + "</p>");
        out.println("            <p><strong>Request URI:</strong> " + request.getRequestURI() + "</p>");
        out.println("            <p><strong>Server Name:</strong> " + request.getServerName() + "</p>");
        out.println("            <p><strong>Server Port:</strong> " + request.getServerPort() + "</p>");
        out.println("        </div>");
        out.println("        <div style='text-align: center;'>");
        out.println("            <a href='index.jsp' class='btn'>Back to Home</a>");
        out.println("            <a href='hello?name=John' class='btn'>Test Parameters</a>");
        out.println("        </div>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect to GET method
        doGet(request, response);
    }
}


