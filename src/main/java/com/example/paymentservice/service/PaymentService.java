package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentCallbackRequest;
import com.example.paymentservice.dto.RequestPayDto;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

public interface PaymentService {
    // 결제 요청 데이터 조회
    RequestPayDto findRequestDto(String orderUid);

    // 결제(콜백)
    IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request);
}

