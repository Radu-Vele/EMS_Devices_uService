package com.devicesus.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceChangeSender {
    private final RabbitTemplate template;
    private final Queue deviceChangeQueue;

    public void send(String message) {
        System.out.println("[x] Sending: " + message);
        this.template.convertAndSend( deviceChangeQueue.getName(), message);
    }
}
