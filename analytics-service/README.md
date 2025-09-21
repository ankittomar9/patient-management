# Analytics Service

## Overview
The Analytics Service is a consumer service in the Patient Management System that processes patient-related events from Kafka to generate analytics and insights. It subscribes to patient events and performs analytical operations in real-time.

## Features
- Consumes patient events from Kafka
- Processes and analyzes patient data
- Built with Spring Boot and Java 21
- Lightweight and horizontally scalable

## Prerequisites
- Java 21
- Maven 3.9.9
- Docker (for containerization)
- Kafka (for event streaming)

## Configuration
Configuration is managed through `application.properties`:
```properties
spring.application.name=analytics-service
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.ByteArrayDeserializer
```

## Kafka Integration
- Consumes from topic: `patient`
- Consumer Group: `analytics-service`
- Message format: Protocol Buffers (PatientEvent)

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
docker build -t analytics-service .
```

### Docker Run
```bash
docker run -p 4002:4002 analytics-service
```

## Event Processing
### Patient Event Structure
```protobuf
message PatientEvent {
    string patient_id = 1;
    string name = 2;
    string email = 3;
    // Additional fields as needed
}
```

## Dependencies
- Spring Boot 3.x
- Spring Kafka
- Protocol Buffers
- Lombok
- Spring Boot Actuator

## Environment Variables
- `KAFKA_BOOTSTRAP_SERVERS` - Kafka bootstrap servers (default: localhost:9092)
- `SERVER_PORT` - Service port (default: 4002)

## Monitoring
- Health Check: `GET /actuator/health`
- Metrics: `GET /actuator/metrics`

## Logging
Logs are configured to output to console with INFO level by default. Each processed event is logged with patient details.

## Scaling
The service is stateless and can be horizontally scaled by running multiple instances with the same consumer group ID.
