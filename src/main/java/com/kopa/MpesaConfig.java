package com.kopa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MpesaConfig {

    @Value("${mpesa.consumer.key}")
    private String consumerKey;

    @Value("${mpesa.consumer.secret}")
    private String consumerSecret;

    @Value("${mpesa.shortcode}")
    private String shortcode;

    @Value("${mpesa.passkey}")
    private String passkey;

    @Value("${mpesa.env}")
    private String env; // sandbox or production

    public String getConsumerKey() { return consumerKey; }
    public String getConsumerSecret() { return consumerSecret; }
    public String getShortcode() { return shortcode; }
    public String getPasskey() { return passkey; }
    public String getEnv() { return env; }
}

