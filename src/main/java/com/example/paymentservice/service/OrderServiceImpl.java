package com.example.paymentservice.service;

import com.example.paymentservice.entity.Member;
import com.example.paymentservice.entity.Order;
import com.example.paymentservice.entity.Pay;
import com.example.paymentservice.entity.PaymentStatus;
import com.example.paymentservice.repository.OrderRepository;
import com.example.paymentservice.repository.PaymentRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;


    @Override
    public Order autoOrder(Member member) {
        // 임시 결제내역 생성
        Pay payment = Pay.builder()
                .price(1000L)
                .status(PaymentStatus.READY)
                .build();

        paymentRepository.save(payment);

        // 주문 생성
        Order order = Order.builder()
                .member(member)
                .price(1000L)
                .itemName("1달러샵 상품")
                .orderUid(UUID.randomUUID().toString())
                .payment(payment)
                .build();

        return orderRepository.save(order);
    }
}
