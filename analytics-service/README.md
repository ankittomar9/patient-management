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
Configuration is managed through `application.properties` or environment variables. Below are the essential properties:

```properties
spring.application.name=analytics-service
server.port=4002

# Kafka Consumer Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=analytics-service
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.ByteArrayDeserializer
```

## Kafka Integration
- **Topic**: Consumes from the `patient` topic.
- **Consumer Group**: `analytics-service`.
- **Message Format**: The service expects messages where the value is a byte array representing a Protocol Buffers message (`PatientEvent`). The application is responsible for deserializing this byte array into the Protobuf object.

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
docker run -p 4002:4002 --env KAFKA_BOOTSTRAP_SERVERS=your_kafka_host:9092 analytics-service
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

## Monitoring
- **Health Check**: `GET /actuator/health`
- **Metrics**: `GET /actuator/metrics`

## Scaling
The service is stateless and can be horizontally scaled by running multiple instances with the same consumer group ID.
