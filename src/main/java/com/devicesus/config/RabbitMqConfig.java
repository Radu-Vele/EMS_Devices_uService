package com.devicesus.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.devices_change_queue}")
    private String devicesChangeQueueName;

    @Bean
    public Queue deviceChangeQueue() {
    return new Queue(devicesChangeQueueName);
    }

}
