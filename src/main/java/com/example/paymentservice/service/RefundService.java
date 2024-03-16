package com.example.paymentservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {
    private final String CANCEL_URL = "https://api.iamport.kr/payments/cancel";
    private final String GET_TOKEN_URL = "https://api.iamport.kr/users/getToken";
    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;

    public void refundRequest(String access_token, String merchant_uid) throws IOException {
        URL url = new URL(CANCEL_URL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청방식 POST 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type, Accept, Authorization 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", access_token);

        // 출력
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("merchant_uid", merchant_uid);

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String res = br.lines().collect(Collectors.joining("\n"));
        br.close();
        conn.disconnect();

        log.info("응답: {}", res);
        log.info("결제 취소 완료 : 주문 번호 {}", merchant_uid);


    }
    public String getToken() throws IOException {
        URL url = new URL(GET_TOKEN_URL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청방식 POST 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type, Accept, Authorization 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        // 출력
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("imp_key", apiKey);
        json.addProperty("imp_secret", secretKey);

        //
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson();
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        String accessToken = gson.fromJson(response, Map.class).get("access_token").toString();
        br.close();

        conn.disconnect();
        log.info("액세스 토큰 = {}", accessToken);
        return accessToken;
    }
}
