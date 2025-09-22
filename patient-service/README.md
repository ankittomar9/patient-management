# Patient Service

## Overview
The Patient Service is a core microservice in the Patient Management System that handles patient-related operations. It exposes REST APIs for patient management and integrates with Kafka for event-driven communication and gRPC for synchronous communication with the Billing Service.

## Features
- REST API endpoints for patient CRUD operations
- Publishes patient events to Kafka for other services to consume
- Communicates with Billing Service via gRPC
- Built with Spring Boot and Java 21

## Prerequisites
- Java 21
- Maven 3.9.9
- Docker (for containerization)
- Kafka (for event streaming)

## Configuration
Configuration is managed through `application.properties`:
```properties
spring.application.name=patient-service
server.port=4000
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.ByteArraySerializer
```

## API Endpoints
- `POST /api/patients` - Create a new patient
- `GET /api/patients` - Get all patients
- `GET /api/patients/{id}` - Get patient by ID
- `PUT /api/patients/{id}` - Update patient
- `DELETE /api/patients/{id}` - Delete patient

## Kafka Integration
- Produces to topic: `patient`
- Message format: Protocol Buffers (PatientEvent)

## gRPC Integration
- gRPC client to Billing Service
- Port: 9001

## Building and Running

### Local Development
```bash
mvn spring-boot:run
```

### Build with Maven
```bash
mvn clean package
```

### Docker Build
```bash
docker build -t patient-service .
```

### Docker Run
```bash
docker run -p 4000:4000 patient-service
```

## Dependencies
- Spring Boot 3.x
- Spring Kafka
- gRPC
- Protocol Buffers
- Spring Web
- Lombok

## Environment Variables
- `KAFKA_BOOTSTRAP_SERVERS` - Kafka bootstrap servers (default: localhost:9092)
- `BILLING_SERVICE_HOST` - Billing Service host (default: localhost)
- `BILLING_SERVICE_PORT` - Billing Service gRPC port (default: 9001)

## Logging
Logs are configured to output to console with INFO level by default.
