package com.saidichemseddine.CloudGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/productServiceFallback")
    public String orderServiceFallback(){
        return "Order Service is taking too long to respond or is down. Please try again later";
    }

    @GetMapping("/orderServiceFallback")
    public String productServiceFallback(){
        return "Product Service is taking too long to respond or is down. Please try again later";
    }

    @GetMapping("/paymentServiceFallback")
    public String paymentServiceFallback(){
        return "Payment Service is taking too long to respond or is down. Please try again later";
    }
}
