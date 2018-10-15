package com.example.Customer.Customer.service;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class CustomerService {

   /* @Autowired
    RestTemplate restTemplate;

   @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

  @Value("${accountService.restapi.url}")
    String accServiceURL;

    @HystrixCommand(fallbackMethod = "alternateMethod")
    public String getAccount( String name ) {

         return restTemplate.getForObject(accServiceURL, String.class, name);
    }


    @SuppressWarnings("unused")
    public String alternateMethod(String name,Throwable t){

        return " CIRCUIT BREAKER IS ENABLED NOW AS the Account service is down. The original exception was " + t.toString();
    }*/


}
