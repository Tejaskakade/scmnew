package com.scmnew.services;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailHtml();

    void sendEmailWithAttachment();

}
