package com.example.paymentservice.controller;

import com.example.paymentservice.dto.PaymentCallbackRequest;
import com.example.paymentservice.dto.RequestPayDto;
import com.example.paymentservice.service.PaymentService;
import com.example.paymentservice.service.RefundService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final RefundService refundService;

    @GetMapping("/payment/{id}")
    public String paymentPage(@PathVariable(name = "id", required = false) String id,
                              Model model) {

        RequestPayDto requestDto = paymentService.findRequestDto(id);
        log.info("requestDto = {}", requestDto);
        model.addAttribute("requestDto", requestDto);
        return "payment";
    }

    @ResponseBody
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallbackRequest request) {
        log.info("호출");
        IamportResponse<Payment> iamportResponse = paymentService.paymentByCallback(request);

        log.info("결제 응답={}", iamportResponse.getResponse().toString());

        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/payment/cancel")
    public String cancelPayment(@RequestParam("merchant_uid") String merchant_uid) throws IOException {
        log.info("호출");
        String token = refundService.getToken();
        refundService.refundRequest(token, merchant_uid);
        return "ok";
    }



    @GetMapping("/success-payment")
    public String successPaymentPage() {
        return "success-payment";
    }

    @GetMapping("/fail-payment")
    public String failPaymentPage() {
        return "fail-payment";
    }
}