package com.kopa.controller;

import com.kopa.service.MpesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mpesa")
@CrossOrigin(origins = "http://localhost:5173")
public class MpesaController {

    @Autowired
    private MpesaService mpesaService;

    @PostMapping("/pay")
    public Map<String, Object> pay(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        String amount = body.get("amount");
        return mpesaService.stkPush(phone, amount, "VIPPayment", "VIP predictions");
    }
}
