# Auth Service

## Overview
The Auth Service is a microservice responsible for handling user authentication and authorization within the patient management system. It provides JWT (JSON Web Token) based authentication and integrates with other services to ensure secure access to the system.

## Features
- User registration and authentication
- JWT token generation and validation
- Role-based access control (RBAC)
- Secure password storage with BCrypt encryption

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate user and get a JWT token
- `POST /api/auth/validate` - Validate a JWT token

### User Management
- `GET /api/users` - Get all users (Admin only)
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user information
- `DELETE /api/users/{id}` - Delete a user (Admin only)

## Flow Structure
1.  **Authentication Flow**:
    -   Client sends credentials to `/api/auth/login`.
    -   The service validates credentials against the database.
    -   If valid, it generates and returns a JWT token.
    -   The client includes this token in the `Authorization` header for subsequent requests.

2.  **Authorization Flow**:
    -   For each protected request, the service validates the JWT token.
    -   It checks the user's roles and permissions.
    -   Access is granted or denied based on the user's privileges.

## Configuration

This service requires the following properties to be configured in your `application.properties` or as environment variables:

```properties
# Server Configuration
server.port=4005

# Database Configuration (PostgreSQL example)
spring.datasource.url=jdbc:postgresql://localhost:5432/auth_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=your_super_secret_key_that_is_long_and_secure
jwt.expiration.ms=86400000 # 24 hours
```

## Security
- All passwords are encrypted using BCrypt.
- JWT tokens are signed with a secret key.
- CSRF protection is enabled by default in Spring Security.

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6.3 or higher

### Steps
1.  Configure the database and JWT properties as described in the **Configuration** section.
2.  Build the project: `./mvnw clean install`
3.  Run the service: `./mvnw spring-boot:run`

## API Documentation (Swagger)
Once the service is running, you can access the Swagger UI at:
`http://localhost:4005/swagger-ui.html`
