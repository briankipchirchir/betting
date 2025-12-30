package com.kopa.service;

import com.kopa.MpesaConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Base64;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class MpesaService {

    private final MpesaConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    public MpesaService(MpesaConfig config) {
        this.config = config;
    }

    // 1️⃣ Get access token
    public String getAccessToken() {
        String url = config.getEnv().equals("sandbox")
                ? "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials"
                : "https://api.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";

        String credentials = config.getConsumerKey() + ":" + config.getConsumerSecret();
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoded);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return (String) response.getBody().get("access_token");
    }

    // 2️⃣ Initiate STK Push
    public Map<String, Object> stkPush(String phoneNumber, String amount, String accountRef, String transactionDesc) {
        String token = getAccessToken();
        String url = config.getEnv().equals("sandbox")
                ? "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest"
                : "https://api.safaricom.co.ke/mpesa/stkpush/v1/processrequest";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String password = Base64.getEncoder().encodeToString(
                (config.getShortcode() + config.getPasskey() + timestamp).getBytes()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String payload = "{"
                + "\"BusinessShortCode\":\"" + config.getShortcode() + "\","
                + "\"Password\":\"" + password + "\","
                + "\"Timestamp\":\"" + timestamp + "\","
                + "\"TransactionType\":\"CustomerPayBillOnline\","
                + "\"Amount\":\"" + amount + "\","
                + "\"PartyA\":\"" + phoneNumber + "\","
                + "\"PartyB\":\"" + config.getShortcode() + "\","
                + "\"PhoneNumber\":\"" + phoneNumber + "\","
                + "\"CallBackURL\":\"https://yourdomain.com/mpesa/callback\","
                + "\"AccountReference\":\"" + accountRef + "\","
                + "\"TransactionDesc\":\"" + transactionDesc + "\""
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        return response.getBody();
    }
}
