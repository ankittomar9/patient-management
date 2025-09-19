package com.company.patientservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;

public class kafkaProducer {

    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    public kafkaProducer(KafkaTemplate<String,byte[]> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }
}
