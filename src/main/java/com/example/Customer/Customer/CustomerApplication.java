package com.example.Customer.Customer;

import com.example.Customer.Customer.service.MessageReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;



@EnableEurekaClient
@SpringBootApplication
//public class CustomerApplication   implements  RabbitListenerConfigurer {
public class CustomerApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApplication.class);


	@Value("${queue.name}")
	private  String  queueName;

	@Autowired
	private ConnectionFactory connectionFactory;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean
	public RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory){
		rabbitTemplate=new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

    //CONSUMER MESSAGE PART
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);//Queue name reads from CONFIG Repository
		container.setMessageListener(listenerAdapter);
		return container;
	}

   @Bean
	MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
		return new MessageListenerAdapter(receiver, "onMessage");
	}
}
