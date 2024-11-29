package com.example.notification_service;

import com.example.notification_service.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
public class NotificationServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(OrderCreatedEvent orderCreatedEvent){
		log.info("order placed successfully-{}",orderCreatedEvent.getOrderNo());
	}

}
