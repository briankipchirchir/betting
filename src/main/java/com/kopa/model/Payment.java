package com.kopa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private Integer amount;
    private String receiptNumber;
    private String checkoutRequestId;
    private String status; // SUCCESS | FAILED

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setAmount(Integer amount) {

    }

    public void setPhone(String phone) {

    }

    public void setReceiptNumber(String receipt) {

    }

    public void setStatus(String success) {

    }

    public void setUser(Object user) {

    }

    // getters & setters
}

