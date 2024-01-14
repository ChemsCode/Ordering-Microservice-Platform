package com.saidichemseddine.PaymentService.service;

import com.saidichemseddine.PaymentService.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
