package com.saidichemseddine.OrderService.service;

import com.saidichemseddine.OrderService.exception.CustomException;
import com.saidichemseddine.OrderService.external.client.PaymentService;
import com.saidichemseddine.OrderService.entity.OrderEntity;
import com.saidichemseddine.OrderService.external.client.ProductService;
import com.saidichemseddine.OrderService.external.request.PaymentRequest;
import com.saidichemseddine.OrderService.external.response.PaymentResponse;
import com.saidichemseddine.OrderService.external.response.ProductResponse;
import com.saidichemseddine.OrderService.model.OrderRequest;
import com.saidichemseddine.OrderService.model.OrderResponse;
import com.saidichemseddine.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("OrderServiceImpl: placeOrder: orderRequest: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating Order with Status: CREATED, for the ProductId: {}", orderRequest.getProductId());
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .amount(orderRequest.getTotalAmount())
                .build();
        orderEntity = orderRepository.save(orderEntity);

        log.info("Calling PaymentService to do the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(orderEntity.getId())
                .amount(orderRequest.getTotalAmount())
                .paymentMode(orderRequest.getPaymentMode())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("PaymentService: doPayment: success, changing the Order status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error while calling PaymentService: doPayment, changing Status PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        orderEntity.setOrderStatus(orderStatus);
        orderRepository.save(orderEntity);


        log.info("Order placed successfully: {}", orderEntity);
        return orderEntity.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("OrderServiceImpl: getOrderDetails: orderId: {}", orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found for orderId: " + orderId, "NOT_FOUND", 404));
        log.info("OrderServiceImpl: getOrderDetails: orderEntity: {}", orderEntity);

        log.info("Calling ProductService to get the Product Details for ProductId: {}", orderEntity.getProductId());
        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + orderEntity.getProductId(), ProductResponse.class);

        OrderResponse.ProductDetails  productDetails
                =       OrderResponse.ProductDetails.builder()
                .productId(productResponse.getProductId())
                .productName(productResponse.getProductName())
                .price(productResponse.getPrice())
                .quantity(orderEntity.getQuantity())
                .build();

        log.info("Getting the Payment Details form the Payment-Service for OrderId: {}", orderId);
        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/" + orderId, PaymentResponse.class);


        OrderResponse.PaymentDetails paymentDetails
                =       OrderResponse.PaymentDetails.builder()
                        .paymentId(paymentResponse.getPaymentId())
                        .paymentMode(paymentResponse.getPaymentMode())
                        .paymentStatus(paymentResponse.getStatus())
                        .paymentDate(paymentResponse.getPaymentDate())
                        .build();

        return OrderResponse.builder()
                .orderId(orderEntity.getId())
                .orderDate(orderEntity.getOrderDate())
                .orderStatus(orderEntity.getOrderStatus())
                .amount(orderEntity.getAmount())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

    }
}
