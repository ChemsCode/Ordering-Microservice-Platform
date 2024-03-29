package com.saidichemseddine.OrderService.controller;

import com.saidichemseddine.OrderService.model.OrderRequest;
import com.saidichemseddine.OrderService.model.OrderResponse;
import com.saidichemseddine.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('Customer')")
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("OrderController: createOrder: orderRequest: {}", orderRequest);
        long orderId = orderService.placeOrder(orderRequest);
        log.info("OrderController: createOrder: orderId: {}", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {
        log.info("OrderController: getOrderDetails: orderId: {}", orderId);
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        log.info("OrderController: getOrderDetails: orderResponse: {}", orderResponse);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
