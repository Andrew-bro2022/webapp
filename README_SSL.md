# SSL Configuration Guide - Java Web Application

## Overview
This document provides step-by-step instructions for configuring SSL/HTTPS for the Java Web Application deployed on AWS EC2 using Session Manager.

## Prerequisites
- AWS EC2 instance running Amazon Linux 2023
- Java Web Application deployed on Tomcat 9
- Nginx installed and configured
- Security groups configured for ports 80 and 443

## Architecture

Internet → Nginx (SSL) → Tomcat (8080) → MySQL RDS
↓
HTTPS (443) + HTTP (80) → Nginx Reverse Proxy


## 1. Security Group Configuration

### 1.1 Required Ports
Ensure the following ports are open in your EC2 security group:

| Port | Protocol | Source | Description |
|------|----------|--------|-------------|
| 22 | TCP | Your IP | SSH Access |
| 80 | TCP | 0.0.0.0/0 | HTTP (redirects to HTTPS) |
| 443 | TCP | 0.0.0.0/0 | HTTPS (SSL) |
| 8080 | TCP | 0.0.0.0/0 | Tomcat (internal) |

### 1.2 Add HTTPS Rule
1. Go to AWS Console → EC2 → Security Groups
2. Select your EC2 security group
3. Add inbound rule:
   - **Type**: HTTPS
   - **Protocol**: TCP
   - **Port**: 443
   - **Source**: 0.0.0.0/0
   - **Description**: Allow HTTPS access

## 2. Nginx Installation and Configuration

### 2.1 Install Nginx
```bash
# Install Nginx
sudo yum install -y nginx

# Start and enable Nginx
sudo systemctl start nginx
sudo systemctl enable nginx

# Check status
sudo systemctl status nginx
```

### 2.2 Install Certbot (Optional - for Let's Encrypt)
```bash
# Install Certbot
sudo yum install -y certbot python3-certbot-nginx

# Verify installation
certbot --version
```

## 3. SSL Certificate Generation

### 3.1 Create SSL Directories
```bash
# Create SSL directories
sudo mkdir -p /etc/ssl/private
sudo mkdir -p /etc/ssl/certs

# Set proper permissions
sudo chmod 700 /etc/ssl/private
sudo chmod 755 /etc/ssl/certs
```

### 3.2 Generate Self-Signed Certificate
```bash
# Generate self-signed SSL certificate
sudo openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/ssl/private/nginx-selfsigned.key \
    -out /etc/ssl/certs/nginx-selfsigned.crt \
    -subj "/C=US/ST=State/L=City/O=Organization/CN=54.221.107.40"

# Set proper permissions for private key
sudo chmod 600 /etc/ssl/private/nginx-selfsigned.key

# Verify certificate files
sudo ls -la /etc/ssl/private/nginx-selfsigned.key
sudo ls -la /etc/ssl/certs/nginx-selfsigned.crt
```

### 3.3 Alternative: Let's Encrypt Certificate (Requires Domain)
```bash
# If you have a domain name, use Let's Encrypt
sudo certbot --nginx -d your-domain.com

# Note: Let's Encrypt doesn't support IP addresses
```

## 4. Nginx SSL Configuration

### 4.1 Create Nginx Configuration
```bash
# Create Nginx configuration file
sudo nano /etc/nginx/conf.d/webapp.conf
```

### 4.2 SSL Configuration Content
```nginx
# HTTP server - redirects to HTTPS
server {
    listen 80;
    server_name 54.221.107.40;  # Replace with your EC2 public IP
    
    # Redirect all HTTP requests to HTTPS
    return 301 https://$server_name$request_uri;
}

# HTTPS server - handles SSL
server {
    listen 443 ssl;
    server_name 54.221.107.40;  # Replace with your EC2 public IP
    
    # SSL certificate configuration
    ssl_certificate /etc/ssl/certs/nginx-selfsigned.crt;
    ssl_certificate_key /etc/ssl/private/nginx-selfsigned.key;
    
    # SSL security settings
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers ECDHE-RSA-AES256-GCM-SHA512:DHE-RSA-AES256-GCM-SHA512:ECDHE-RSA-AES256-GCM-SHA384:DHE-RSA-AES256-GCM-SHA384;
    ssl_prefer_server_ciphers off;
    
    # Reverse proxy to Tomcat
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # Additional proxy settings
        proxy_connect_timeout 30s;
        proxy_send_timeout 30s;
        proxy_read_timeout 30s;
    }
}
```

### 4.3 Test and Apply Configuration
```bash
# Test Nginx configuration
sudo nginx -t

# Reload Nginx configuration
sudo systemctl reload nginx

# Restart Nginx if needed
sudo systemctl restart nginx

# Check Nginx status
sudo systemctl status nginx
```

## 5. Verification and Testing

### 5.1 Test HTTPS Access
```bash
# Test HTTPS access (ignore certificate warnings)
curl -k https://54.221.107.40/

# Test application HTTPS access
curl -k https://54.221.107.40/webapp/

# Test HTTP redirect
curl -I http://54.221.107.40/
```

### 5.2 Check Port Listening
```bash
# Check if ports are listening
sudo netstat -tlnp | grep :443
sudo netstat -tlnp | grep :80
sudo netstat -tlnp | grep :8080
```

### 5.3 Browser Testing
- **HTTPS URL**: `https://54.221.107.40/webapp/`
- **Expected**: Browser will show "Not Secure" warning (normal for self-signed certificates)
- **Action**: Click "Advanced" → "Proceed to site" to continue

## 6. Troubleshooting

### 6.1 Common Issues

#### Nginx Configuration Error
```bash
# Check Nginx configuration syntax
sudo nginx -t

# Check Nginx error logs
sudo tail -f /var/log/nginx/error.log
```

#### SSL Certificate Issues
```bash
# Verify certificate files exist
sudo ls -la /etc/ssl/private/nginx-selfsigned.key
sudo ls -la /etc/ssl/certs/nginx-selfsigned.crt

# Check certificate details
sudo openssl x509 -in /etc/ssl/certs/nginx-selfsigned.crt -text -noout
```

#### Port Access Issues
```bash
# Check if ports are open
sudo netstat -tlnp | grep :443
sudo netstat -tlnp | grep :80

# Check firewall status
sudo iptables -L
```

### 6.2 Service Management
```bash
# Start/Stop/Restart Nginx
sudo systemctl start nginx
sudo systemctl stop nginx
sudo systemctl restart nginx

# Check Nginx status
sudo systemctl status nginx

# Enable/Disable auto-start
sudo systemctl enable nginx
sudo systemctl disable nginx
```

## 7. Security Considerations

### 7.1 Self-Signed Certificate Limitations
- **Browser Warning**: Browsers will show "Not Secure" warning
- **No CA Validation**: Certificate is not validated by Certificate Authority
- **Suitable For**: Development, testing, internal use, demonstrations

### 7.2 Production Recommendations
- **Use Domain Name**: Purchase a domain and configure DNS
- **Let's Encrypt Certificate**: Free, automatically renewed SSL certificates
- **Certificate Validation**: Proper CA-signed certificates for production

### 7.3 SSL Security Settings
The configuration includes:
- **TLS 1.2 and 1.3**: Modern, secure protocols
- **Strong Ciphers**: AES-256-GCM encryption
- **HTTP Redirect**: All HTTP traffic redirected to HTTPS

## 8. Maintenance

### 8.1 Certificate Renewal
```bash
# Self-signed certificates expire after 365 days
# To renew, regenerate the certificate:
sudo openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/ssl/private/nginx-selfsigned.key \
    -out /etc/ssl/certs/nginx-selfsigned.crt \
    -subj "/C=US/ST=State/L=City/O=Organization/CN=54.221.107.40"

# Restart Nginx
sudo systemctl restart nginx
```

### 8.2 Log Monitoring
```bash
# Monitor Nginx access logs
sudo tail -f /var/log/nginx/access.log

# Monitor Nginx error logs
sudo tail -f /var/log/nginx/error.log

# Monitor system logs
sudo journalctl -u nginx -f
```

## 9. Application URLs

### 9.1 Access URLs
- **Home Page**: `https://54.221.107.40/webapp/`
- **User Login**: `https://54.221.107.40/webapp/login`
- **User Registration**: `https://54.221.107.40/webapp/register`
- **Test Servlet**: `https://54.221.107.40/webapp/hello`

### 9.2 HTTP Redirect
- **HTTP Access**: `http://54.221.107.40/webapp/` → Redirects to HTTPS
- **Automatic Redirect**: All HTTP traffic automatically redirected to HTTPS

## 10. Team Collaboration Notes

### 10.1 Configuration Files
- **Nginx Config**: `/etc/nginx/conf.d/webapp.conf`
- **SSL Certificate**: `/etc/ssl/certs/nginx-selfsigned.crt`
- **SSL Private Key**: `/etc/ssl/private/nginx-selfsigned.key`

### 10.2 Important Commands
```bash
# Quick status check
sudo systemctl status nginx
sudo netstat -tlnp | grep :443

# Quick restart
sudo systemctl restart nginx

# Quick test
curl -k https://54.221.107.40/webapp/
```

### 10.3 Backup Recommendations
- **Backup SSL certificates** before making changes
- **Document any custom configurations** for team reference
- **Test SSL configuration** after any system updates

---

**Last Updated**: September 15, 2025
**Maintainer**: [Your Name]
**Version**: 1.0
**Environment**: AWS EC2 (Amazon Linux 2023) + Nginx + Tomcat 9