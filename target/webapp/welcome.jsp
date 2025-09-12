<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.webapp.model.User" %>
<%@ page import="java.util.Date" %>
<%
    User user = (User) request.getAttribute("user");
    Integer totalUsers = (Integer) request.getAttribute("totalUsers");
    Long daysSinceRegistration = (Long) request.getAttribute("daysSinceRegistration");
    Date loginTime = (Date) request.getAttribute("loginTime");
    String currentTime = (String) request.getAttribute("currentTime");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Page - Java Web Application</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .welcome-container {
            max-width: 1000px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px;
            text-align: center;
        }
        
        .header h1 {
            font-size: 36px;
            margin-bottom: 10px;
        }
        
        .header p {
            font-size: 18px;
            opacity: 0.9;
        }
        
        .content {
            padding: 40px;
        }
        
        .user-info {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 30px;
            margin-bottom: 30px;
            border-left: 5px solid #667eea;
        }
        
        .user-info h2 {
            color: #333;
            margin-bottom: 20px;
            font-size: 24px;
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }
        
        .info-item {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
        }
        
        .info-item h3 {
            color: #667eea;
            margin-bottom: 10px;
            font-size: 16px;
        }
        
        .info-item p {
            color: #333;
            font-size: 18px;
            font-weight: 500;
        }
        
        .stats-section {
            background: #e8f4fd;
            border-radius: 10px;
            padding: 30px;
            margin-bottom: 30px;
        }
        
        .stats-section h2 {
            color: #0c5460;
            margin-bottom: 20px;
            font-size: 24px;
        }
        
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }
        
        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
        }
        
        .stat-number {
            font-size: 32px;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 10px;
        }
        
        .stat-label {
            color: #666;
            font-size: 14px;
        }
        
        .actions {
            text-align: center;
            margin-top: 30px;
        }
        
        .btn {
            display: inline-block;
            padding: 12px 30px;
            margin: 0 10px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 500;
            transition: transform 0.2s;
        }
        
        .btn:hover {
            transform: translateY(-2px);
        }
        
        .btn-secondary {
            background: linear-gradient(135deg, #6c757d 0%, #495057 100%);
        }
        
        .time-info {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 8px;
            padding: 20px;
            margin-top: 20px;
        }
        
        .time-info h3 {
            color: #155724;
            margin-bottom: 10px;
        }
        
        .time-info p {
            color: #155724;
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <div class="welcome-container">
        <div class="header">
            <h1>🎉 Welcome Back!</h1>
            <p>You have successfully logged into Java Web Application</p>
        </div>
        
        <div class="content">
            <div class="user-info">
                <h2>👤 User Information</h2>
                <div class="info-grid">
                    <div class="info-item">
                        <h3>Username</h3>
                        <p><%= user.getUsername() %></p>
                    </div>
                    <div class="info-item">
                        <h3>Full Name</h3>
                        <p><%= user.getFullName() != null ? user.getFullName() : "Not Set" %></p>
                    </div>
                    <div class="info-item">
                        <h3>Email</h3>
                        <p><%= user.getEmail() != null ? user.getEmail() : "Not Set" %></p>
                    </div>
                    <div class="info-item">
                        <h3>User ID</h3>
                        <p>#<%= user.getId() %></p>
                    </div>
                </div>
            </div>
            
            <div class="stats-section">
                <h2>📊 System Statistics</h2>
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-number"><%= totalUsers %></div>
                        <div class="stat-label">Total Users</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number"><%= daysSinceRegistration %></div>
                        <div class="stat-label">Days Since Registration</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number">1</div>
                        <div class="stat-label">Online Status</div>
                    </div>
                </div>
            </div>
            
            <div class="time-info">
                <h3>⏰ Time Information</h3>
                <p><strong>Current Time:</strong> <%= currentTime %></p>
                <p><strong>Login Time:</strong> <%= loginTime != null ? new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loginTime) : "Unknown" %></p>
                <p><strong>Registration Time:</strong> <%= user.getCreatedAt() != null ? user.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "Unknown" %></p>
            </div>
            
            <div class="actions">
                <a href="hello" class="btn">Test Servlet</a>
                <a href="index.jsp" class="btn btn-secondary">Back to Home</a>
                <a href="logout" class="btn btn-secondary" onclick="return confirm('Are you sure you want to logout?')">Logout</a>
            </div>
        </div>
    </div>
    
    <script>
        // Auto refresh page time (every 30 seconds)
        setTimeout(function() {
            location.reload();
        }, 30000);
    </script>
</body>
</html>
