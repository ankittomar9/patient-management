# Billing Service

## Overview
The Billing Service handles all billing-related operations in the Patient Management System. It exposes a gRPC interface for other services to interact with and is responsible for managing patient billing information.

## Features
- gRPC server for billing operations
- Handles billing account creation and management
- Built with Spring Boot and Java 21
- Uses Protocol Buffers for service definition

## Prerequisites
- Java 21
- Maven 3.9.9
- Docker (for containerization)
- gRPC tools for protocol buffer compilation

## Configuration
Configuration is managed through `application.properties`:
```properties
spring.application.name=billing-service
server.port=4001
grpc.server.port=9001
```

## gRPC Service Definition
Service is defined in `billing_service.proto` and includes:
- `CreateBillingAccount` - Creates a new billing account for a patient
- `GetBillingAccount` - Retrieves billing account information
- `UpdateBillingInfo` - Updates billing information

## Building and Running

### Local Development
```bash
mvn spring-boot:run
```

### Build with Maven
```bash
mvn clean package
```

### Generate gRPC Code
```bash
mvn compile
```

### Docker Build
```bash
docker build -t billing-service .
```

### Docker Run
```bash
docker run -p 4001:4001 -p 9001:9001 billing-service
```

## gRPC Endpoints
- gRPC Server Port: 9001
- Health Check: 4001/actuator/health

## Dependencies
- Spring Boot 3.x
- gRPC Spring Boot Starter
- Protocol Buffers
- Spring Boot Actuator
- Lombok

## Environment Variables
- `GRPC_SERVER_PORT` - gRPC server port (default: 9001)
- `SERVER_PORT` - HTTP server port (default: 4001)

## Testing gRPC Endpoints
You can test the gRPC endpoints using a gRPC client like `grpcurl` or BloomRPC. Example using grpcurl:

```bash
grpcurl -plaintext -d '{"patientId": "123"}' localhost:9001 billing.BillingService/CreateBillingAccount
```

## Logging
Logs are configured to output to console with INFO level by default. gRPC debug logging can be enabled by setting the appropriate log level in `application.properties`.
