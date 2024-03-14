package com.example.paymentservice.service;

import com.example.paymentservice.entity.Member;
import com.example.paymentservice.entity.Order;

public interface OrderService {
    Order autoOrder(Member member);
}
