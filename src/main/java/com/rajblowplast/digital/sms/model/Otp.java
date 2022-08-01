package com.rajblowplast.digital.sms.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("OTP_DATA")
public class Otp {
    @Id
    private String id;

    private String email;
    private int otpId;
    private String otp;
    private String generatedOn;

    public Otp() {}

    public Otp(String email, int otpId, String otp) {
        this.email = email;
        this.otpId = otpId;
        this.otp = otp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOtpId() {
        return otpId;
    }

    public void setOtpId(int otpId) {
        this.otpId = otpId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(String generatedOn) {
        this.generatedOn = generatedOn;
    }
}
