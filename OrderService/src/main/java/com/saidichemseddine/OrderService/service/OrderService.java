package com.saidichemseddine.OrderService.service;

import com.saidichemseddine.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
