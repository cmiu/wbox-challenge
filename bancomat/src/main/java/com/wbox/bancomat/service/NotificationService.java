package com.wbox.bancomat.service;

public interface NotificationService {
    void sendEmail(String message);

    void sendSMS(String message);
}
