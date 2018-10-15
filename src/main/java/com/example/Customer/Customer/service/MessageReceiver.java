package com.example.Customer.Customer.service;


import com.example.Customer.Customer.Repository.CustomerRepository;
import com.example.Customer.Customer.domain.CustomerDomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
   @Autowired
   CustomerRepository customerRepo;
   private ObjectMapper objectMapper = new ObjectMapper();

   @Override
   public void onMessage(final Message message){
        try{
            CustomerDomain customer = objectMapper.readValue(message.getBody(), CustomerDomain.class);
            String custID=customer.getId();
            LOGGER.debug("##################Received message from Rabbit MQ  -ID  {} ", custID );
            LOGGER.debug("##################Received message from Rabbit MQ  -NAME  {} ", customer.getName() );
            LOGGER.debug("##################Received message from Rabbit MQ  -AGE   {} ", customer.getAge() );
            //Persists the record into MongoDatabase
            LOGGER.debug("######  BEFORE SAVE INTO  MONGODB ");
            customerRepo.insert(customer);
            LOGGER.debug("######  after  SAVE INTO  MONGODB ");
            CustomerDomain dbCustObj= customerRepo.findById(custID);
            LOGGER.debug("######Received data from MONGODB  -ID  {} ",dbCustObj.getId() );
        }
        catch (Exception e) {
             LOGGER.error("method=handleMessage message=dataOrApplicationError", e);
        }
    }
}
