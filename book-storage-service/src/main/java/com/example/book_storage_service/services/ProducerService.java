package com.example.book_storage_service.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    public static String BOOK_TOPIC = "add-book-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookId(String topic, String bookId) {
        kafkaTemplate.send(topic, bookId);
    }
}

