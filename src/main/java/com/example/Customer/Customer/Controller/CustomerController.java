package com.example.Customer.Customer.Controller;


import com.example.Customer.Customer.domain.CustomerDomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Value("${routingkey.name}")
    private String routingKey;
    @Value("${exchange.name}")
    private  String  exchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value="/customer/{name}")
    public String getAccount(@PathVariable("name") String name ) {

      //  return customerService.getAccount(name);
        return "Hi from PCF" ;
     }

    @PostMapping (value="/sendEvent")
    public ResponseEntity sendMessage(@RequestBody CustomerDomain customer) {
        LOGGER.debug("INSIDE  CONTROLLER &&&&&&&&&&&&&&&&&&&&&&&    BEFORE SENDING RABBITMQ MESSAGE --->");
        try{
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(customer)).setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
            rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
            LOGGER.debug("INSIDE  CONTROLLER &&&&&&&&&&&&&&&&     AFTER  SENDING RABBITMQ MESSAGE --->");
        }
        catch (Exception ex) {
            LOGGER.error("Unable to serialize customer entry", ex);
        }
        return new ResponseEntity(customer, HttpStatus.OK);
    }
}
