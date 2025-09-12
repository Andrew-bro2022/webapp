<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page - Java Web Application</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .error-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
        }
        
        .error-icon {
            font-size: 64px;
            margin-bottom: 20px;
        }
        
        .error-title {
            color: #333;
            font-size: 28px;
            margin-bottom: 15px;
        }
        
        .error-message {
            color: #666;
            font-size: 16px;
            margin-bottom: 30px;
            line-height: 1.5;
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
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">⚠️</div>
        <h1 class="error-title">Error Occurred</h1>
        <div class="error-message">
            <%= request.getAttribute("errorMessage") != null ? 
                request.getAttribute("errorMessage") : 
                "Sorry, the system encountered an error. Please try again later." %>
        </div>
        <div>
            <a href="index.jsp" class="btn">Back to Home</a>
            <a href="login" class="btn">Login Again</a>
        </div>
    </div>
</body>
</html>
