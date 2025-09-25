# API Gateway

## Overview
The API Gateway serves as the single entry point for all client requests in the Patient Management System. It handles request routing, load balancing, and security, ensuring efficient communication between clients and microservices.

## Features
- Request routing to appropriate microservices
- Load balancing across service instances
- Authentication and authorization
- Rate limiting and circuit breaking
- Request/response transformation
- Service discovery integration

## Architecture
```
Client Requests → API Gateway → Microservices
                      ↓
           Authentication & Authorization
                      ↓
              Request Routing
                      ↓
           Load Balancing (if applicable)
                      ↓
          Response Aggregation (if needed)
                      ↓
                Client Response
```

## Configuration
### Application Properties
```properties
server.port=8080
spring.application.name=api-gateway

# Service Discovery (if using Eureka)
spring.cloud.discovery.enabled=true
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/

# Routes Configuration
spring.cloud.gateway.routes[0].id=auth-service
spring.cloud.gateway.routes[0].uri=lb://auth-service
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/auth/**

spring.cloud.gateway.routes[1].id=patient-service
spring.cloud.gateway.routes[1].uri=lb://patient-service
spring.cloud.gateway.routes[1].predicates[0]=Path=/api/patients/**

# Circuit Breaker Configuration
spring.cloud.gateway.routes[0].filters[0]=CircuitBreaker=authServiceCircuitBreaker
spring.cloud.gateway.routes[1].filters[0]=CircuitBreaker=patientServiceCircuitBreaker
```

## API Endpoints
| Service | Path | Description |
|---------|------|-------------|
| Auth Service | `/api/auth/**` | Authentication and authorization endpoints |
| Patient Service | `/api/patients/**` | Patient management endpoints |
| Billing Service | `/api/billing/**` | Billing related endpoints |
| Analytics Service | `/api/analytics/**` | Analytics and reporting endpoints |

## Security
- JWT token validation for authenticated routes
- Role-based access control (RBAC)
- CORS configuration
- Rate limiting to prevent abuse

## Dependencies
- Spring Cloud Gateway
- Spring Security
- Spring Cloud Circuit Breaker
- Spring Cloud LoadBalancer
- Netflix Eureka Client (for service discovery)
- JWT for authentication

## Getting Started
1. Ensure all required services are running (Eureka Server, Auth Service, etc.)
2. Configure the application properties as needed
3. Build the project: `./mvnw clean install`
4. Run the gateway: `./mvnw spring-boot:run`

The gateway will be available at `http://localhost:8080`

## Monitoring
- Actuator endpoints enabled at `/actuator`
- Health check: `GET /actuator/health`
- Gateway metrics: `GET /actuator/gateway`

## Logging
Logs are configured to output to console with INFO level by default. Request/response logging can be enabled for debugging purposes.

## Scaling
The gateway is stateless and can be horizontally scaled behind a load balancer for high availability.
