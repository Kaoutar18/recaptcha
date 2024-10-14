package com.example.recaptcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {

    @Value("${recaptcha.secret-key}")
    private String recaptchaSecret;

    private final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyCaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = new HashMap<>();
        body.put("secret", recaptchaSecret);
        body.put("response", captchaResponse);


        Map<String, Object> response = restTemplate.postForObject(GOOGLE_RECAPTCHA_VERIFY_URL, body, Map.class);

        return (Boolean) response.get("success");
    }
}
