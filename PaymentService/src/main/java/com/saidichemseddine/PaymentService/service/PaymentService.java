package com.saidichemseddine.PaymentService.service;

import com.saidichemseddine.PaymentService.model.PaymentRequest;
import com.saidichemseddine.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
