package com.kopa.controller;

import com.kopa.model.Payment;
import com.kopa.repository.PaymentRepository;
import com.kopa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/mpesa")
public class MpesaCallbackController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/callback")
    public void mpesaCallback(@RequestBody Map<String, Object> payload) {

        System.out.println("📥 MPESA CALLBACK RECEIVED");
        System.out.println(payload);

        Map body = (Map) payload.get("Body");
        if (body == null) return;

        Map stk = (Map) body.get("stkCallback");
        if (stk == null) return;

        Integer resultCode = (Integer) stk.get("ResultCode");
        String resultDesc = (String) stk.get("ResultDesc");

        if (resultCode != null && resultCode == 0) {

            Map metadata = (Map) stk.get("CallbackMetadata");

            String receipt = null;
            Integer amount = null;
            String phone = null;

            for (Object obj : (Iterable<?>) metadata.get("Item")) {
                Map item = (Map) obj;
                switch (item.get("Name").toString()) {
                    case "MpesaReceiptNumber" -> receipt = item.get("Value").toString();
                    case "Amount" -> amount = Integer.parseInt(item.get("Value").toString());
                    case "PhoneNumber" -> phone = item.get("Value").toString();
                }
            }

            // ✅ Make variables final for lambda
            final String finalReceipt = receipt;
            final Integer finalAmount = amount;
            final String finalPhone = phone;

            userRepository.findByPhone(finalPhone).ifPresent(user -> {

                Payment payment = new Payment();
                payment.setAmount(finalAmount);
                payment.setPhone(finalPhone);
                payment.setReceiptNumber(finalReceipt);
                payment.setStatus("SUCCESS");
                payment.setUser(user);

                paymentRepository.save(payment);

                // 🔐 Activate VIP
                user.setVip(true);
                user.setVipExpiry(LocalDateTime.now().plusDays(30));

                userRepository.save(user);

                System.out.println("✅ VIP ACTIVATED for " + user.getUsername());
            });

        } else {
            System.out.println("❌ PAYMENT FAILED: " + resultDesc);
        }
    }

}

