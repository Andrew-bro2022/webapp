# AWS Deployment Guide - Java Web Application

## Project Overview
- **Project Name**: Java Web Application
- **Technology Stack**: Java 1.8, Maven, Tomcat 9, MySQL 8.0.43
- **Deployment Environment**: AWS EC2 (Amazon Linux 2023) + RDS MySQL
- **Application Type**: Pure Java Web Application (No Framework)

## Architecture Diagram
```
Internet → EC2 (Tomcat) → RDS MySQL
    ↓
Security Groups
    ↓
VPC + Subnets
```

## 1. AWS Resource Preparation

### 1.1 EC2 Instance
- **Instance Type**: t3.micro (Free Tier)
- **Operating System**: Amazon Linux 2023
- **Storage**: 8GB GP3
- **Security Groups**: 
  - SSH (22) - From your IP
  - HTTP (80) - From anywhere
  - HTTPS (443) - From anywhere
  - Tomcat (8080) - From anywhere

### 1.2 RDS Instance
- **Engine**: MySQL 8.0.43
- **Instance Type**: db.t3.micro (Free Tier)
- **Storage**: 20GB GP2
- **Security Group**: Allow EC2 security group access to port 3306
- **Database Name**: webapp_db

## 2. EC2 Environment Configuration

### 2.1 Connect to EC2
```bash
# Using AWS Session Manager
aws ssm start-session --target i-your-instance-id

# Or using SSH
ssh -i your-key.pem ec2-user@your-ec2-public-ip
```

### 2.2 Update System
```bash
sudo yum update -y
```

### 2.3 Install Java 1.8
```bash
# Install OpenJDK 1.8
sudo yum install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel

# Verify installation
java -version
javac -version

# Set JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc
```

### 2.4 Install Maven
```bash
# Download Maven
cd /opt
sudo wget https://archive.apache.org/dist/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz

# Extract
sudo tar -xzf apache-maven-3.8.8-bin.tar.gz
sudo mv apache-maven-3.8.8 maven

# Set environment variables
echo 'export MAVEN_HOME=/opt/maven' >> ~/.bashrc
echo 'export PATH=$MAVEN_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc

# Verify installation
mvn -version
```

### 2.5 Install Tomcat 9
```bash
# Create tomcat user
sudo useradd -r -m -U -d /opt/tomcat -s /bin/false tomcat

# Download Tomcat 9
cd /tmp
wget https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.109/bin/apache-tomcat-9.0.109.tar.gz

# Extract to /opt/tomcat
sudo tar -xzf apache-tomcat-9.0.109.tar.gz -C /opt/tomcat --strip-components=1

# Set permissions
sudo chown -R tomcat:tomcat /opt/tomcat
sudo chmod +x /opt/tomcat/bin/*.sh

# Create systemd service file
sudo tee /etc/systemd/system/tomcat.service > /dev/null <<EOF
[Unit]
Description=Apache Tomcat 9
After=network.target

[Service]
Type=forking
User=tomcat
Group=tomcat
Environment="JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk"
Environment="CATALINA_PID=/opt/tomcat/temp/tomcat.pid"
Environment="CATALINA_HOME=/opt/tomcat"
Environment="CATALINA_BASE=/opt/tomcat"
Environment="CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC"
ExecStart=/opt/tomcat/bin/startup.sh
ExecStop=/opt/tomcat/bin/shutdown.sh
RestartSec=10
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Start Tomcat service
sudo systemctl daemon-reload
sudo systemctl enable tomcat
sudo systemctl start tomcat
sudo systemctl status tomcat
```

### 2.6 Install MySQL Client
```bash
# Install MySQL client
sudo yum install -y mysql

# Verify installation
mysql --version
```

### 2.7 Install Git
```bash
# Install Git
sudo yum install -y git

# Verify installation
git --version
```

## 3. Application Deployment

### 3.1 Clone Project
```bash
# Clone project to EC2
git clone https://github.com/your-username/your-repo.git
cd your-repo

# Or upload project files
# Use scp or rsync to upload project files
```

### 3.2 Configure Database Connection
```bash
# Edit AWS database configuration file
nano src/main/resources/database-aws.properties
```

Ensure configuration is correct:
```properties
# AWS RDS Database Configuration
db.url=jdbc:mysql://your-rds-endpoint:3306/webapp_db?useSSL=true&serverTimezone=UTC
db.username=admin
db.password=your-rds-password
db.driver=com.mysql.cj.jdbc.Driver
```

### 3.3 Compile Project
```bash
# Set environment variable
export APP_ENVIRONMENT=aws

# Compile project
mvn clean compile package

# Check generated WAR file
ls -la target/*.war
```

### 3.4 Deploy to Tomcat
```bash
# Stop Tomcat
sudo systemctl stop tomcat

# Deploy WAR file
sudo cp target/webapp.war /opt/tomcat/webapps/

# Set permissions
sudo chown tomcat:tomcat /opt/tomcat/webapps/webapp.war

# Start Tomcat
sudo systemctl start tomcat

# Check deployment status
sudo systemctl status tomcat
```

### 3.5 Configure Tomcat Environment Variables
```bash
# Edit Tomcat configuration
sudo nano /opt/tomcat/conf/tomcat.conf

# Add environment variable
JAVA_OPTS="-Dapp.environment=aws"

# Restart Tomcat
sudo systemctl restart tomcat
```

## 4. Database Initialization

### 4.1 Connect to RDS
```bash
# Connect to RDS database
mysql -h your-rds-endpoint -u admin -p

# Create database
CREATE DATABASE webapp_db;
USE webapp_db;
```

### 4.2 Execute Initialization Script
```bash
# Execute SQL script on EC2
mysql -h your-rds-endpoint -u admin -p webapp_db < database_init.sql
```

## 5. Verify Deployment

### 5.1 Check Service Status
```bash
# Check Tomcat status
sudo systemctl status tomcat

# Check port listening
sudo netstat -tlnp | grep :8080

# Check logs
sudo tail -f /opt/tomcat/logs/catalina.out
```

### 5.2 Test Application
```bash
# Test application access
curl http://localhost:8080/webapp/

# Test login page
curl http://localhost:8080/webapp/login
```

### 5.3 External Access
- **Application URL**: `http://your-ec2-public-ip:8080/webapp/`
- **Login Page**: `http://your-ec2-public-ip:8080/webapp/login`
- **Registration Page**: `http://your-ec2-public-ip:8080/webapp/register`

## 6. Monitoring and Maintenance

### 6.1 Log Viewing
```bash
# Tomcat logs
sudo tail -f /opt/tomcat/logs/catalina.out

# Application logs
sudo tail -f /opt/tomcat/logs/localhost.*.log

# System logs
sudo journalctl -u tomcat -f
```

### 6.2 Service Management
```bash
# Start service
sudo systemctl start tomcat

# Stop service
sudo systemctl stop tomcat

# Restart service
sudo systemctl restart tomcat

# Check status
sudo systemctl status tomcat
```

### 6.3 Application Updates
```bash
# Stop Tomcat
sudo systemctl stop tomcat

# Backup old version
sudo cp /opt/tomcat/webapps/webapp.war /opt/tomcat/webapps/webapp.war.backup

# Deploy new version
sudo cp target/webapp.war /opt/tomcat/webapps/

# Start Tomcat
sudo systemctl start tomcat
```

## 7. Troubleshooting

### 7.1 Common Issues
1. **Port Access Issues**: Check security group configuration
2. **Database Connection Failed**: Check RDS security group and network ACL
3. **Application Won't Start**: Check log files
4. **Environment Variables Not Working**: Restart Tomcat service

### 7.2 Debug Commands
```bash
# Check Java processes
ps aux | grep java

# Check port usage
sudo netstat -tlnp | grep :8080

# Check firewall
sudo iptables -L

# Check system resources
free -h
df -h
```

## 8. Security Recommendations

### 8.1 Network Security
- Restrict SSH access IP ranges
- Use security group minimum privilege principle
- Regularly update security group rules

### 8.2 Application Security
- Regularly update dependencies
- Use HTTPS (optional)
- Regularly backup database
- Implement password hashing (BCrypt)
- Validate user inputs

## 9. Cost Optimization

### 9.1 Free Tier Usage
- EC2: 750 hours/month
- RDS: 750 hours/month
- Storage: 30GB

### 9.2 Cost Monitoring
- Regularly check AWS bills
- Set up cost alerts
- Optimize instance sizes

## 10. Contact Information

If you encounter issues, please check:
1. Troubleshooting section of this document
2. AWS CloudWatch logs
3. Tomcat log files

---

**Last Updated**: September 2025
**Maintainer**: [Your Name]
**Version**: 1.0
