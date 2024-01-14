package com.saidichemseddine.OrderService.service;

import com.saidichemseddine.OrderService.model.OrderRequest;
import com.saidichemseddine.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
