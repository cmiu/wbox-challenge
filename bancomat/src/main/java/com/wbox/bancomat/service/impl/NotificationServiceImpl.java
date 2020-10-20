package com.wbox.bancomat.service.impl;

import com.wbox.bancomat.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendEmail(String message) {
        System.out.println("Email:" + message);
    }

    @Override
    public void sendSMS(String message) {
        System.out.println("SMS:" + message);
    }
}
