package com.rajblowplast.digital.sms.model;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private Status status;
    private final String jwttoken;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LoginResponse(String name, String jwttoken) {
        this.username = name;
        this.jwttoken = jwttoken;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getToken() {
        return this.jwttoken;
    }
}