<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Web Application</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .info-box {
            background: #e8f4fd;
            border: 1px solid #bee5eb;
            border-radius: 4px;
            padding: 15px;
            margin: 20px 0;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 10px 5px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .feature-list {
            list-style-type: none;
            padding: 0;
        }
        .feature-list li {
            padding: 8px 0;
            border-bottom: 1px solid #eee;
        }
        .feature-list li:before {
            content: "✓ ";
            color: #28a745;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🚀 Java Web Application</h1>
        
        <div class="info-box">
            <h3>Project Information</h3>
            <p><strong>Project Name:</strong> ${pageContext.servletContext.servletContextName}</p>
            <p><strong>Server Info:</strong> <%= application.getServerInfo() %></p>
            <p><strong>Java Version:</strong> <%= System.getProperty("java.version") %></p>
            <p><strong>Current Time:</strong> <%= new java.util.Date() %></p>
        </div>
        
        <h3>Features</h3>
        <ul class="feature-list">
            <li>Maven Project Management</li>
            <li>Servlet 4.0 Support</li>
            <li>JSP 2.3 Support</li>
            <li>JSTL Tag Library</li>
            <li>Responsive Design</li>
            <li>UTF-8 Encoding Support</li>
        </ul>
        
        <div style="text-align: center; margin-top: 30px;">
            <a href="login" class="btn">User Login</a>
            <a href="register" class="btn">User Registration</a>
            <a href="hello" class="btn">Test Servlet</a>
            <a href="https://maven.apache.org/" target="_blank" class="btn">Maven Documentation</a>
        </div>
    </div>
</body>
</html>
