package com.saidichemseddine.PaymentService.service;

import com.saidichemseddine.PaymentService.entity.TransactionDetails;
import com.saidichemseddine.PaymentService.model.PaymentMode;
import com.saidichemseddine.PaymentService.model.PaymentRequest;
import com.saidichemseddine.PaymentService.model.PaymentResponse;
import com.saidichemseddine.PaymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("PaymentService: doPayment: start: paymentRequest: {}", paymentRequest);

        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(String.valueOf(paymentRequest.getPaymentMode()))
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetailsRepository.save(transactionDetails);

        log.info("PaymentService: doPayment: end: transactionDetails: {}", transactionDetails);

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("PaymentService: getPaymentDetailsByOrderId: start: orderId: {}", orderId);
        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(Long.parseLong(orderId));

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentDate(transactionDetails.getPaymentDate())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .orderId(transactionDetails.getOrderId())
                .build();

        return paymentResponse;
    }
}
