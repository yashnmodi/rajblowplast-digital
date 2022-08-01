package com.rajblowplast.digital.sms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Auth {
    private String authMode;
    private int authId;
    private String mailOtp;
    private String authEmail;
    private String mobOtp;
    private String authMobile;
    private String authAction;
    private String authRedirection;

    public Auth() {}

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public String getMailOtp() {
        return mailOtp;
    }

    public void setMailOtp(String mailOtp) {
        this.mailOtp = mailOtp;
    }

    public String getMobOtp() {
        return mobOtp;
    }

    public void setMobOtp(String mobOtp) {
        this.mobOtp = mobOtp;
    }

    public String getAuthAction() {
        return authAction;
    }

    public void setAuthAction(String authAction) {
        this.authAction = authAction;
    }

    public String getAuthRedirection() {
        return authRedirection;
    }

    public void setAuthRedirection(String authRedirection) {
        this.authRedirection = authRedirection;
    }

    public String getAuthEmail() {
        return authEmail;
    }

    public void setAuthEmail(String authEmail) {
        this.authEmail = authEmail;
    }

    public String getAuthMobile() {
        return authMobile;
    }

    public void setAuthMobile(String authMobile) {
        this.authMobile = authMobile;
    }
}
