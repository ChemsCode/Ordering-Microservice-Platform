package com.saidichemseddine.OrderService.service;

import com.saidichemseddine.OrderService.entity.OrderEntity;
import com.saidichemseddine.OrderService.model.OrderRequest;
import com.saidichemseddine.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("OrderServiceImpl: placeOrder: orderRequest: {}", orderRequest);
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .amount(orderRequest.getTotalAmount())
                .build();
        orderEntity = orderRepository.save(orderEntity);

        log.info("Order placed successfully: {}", orderEntity);
        return orderEntity.getId();
    }
}
