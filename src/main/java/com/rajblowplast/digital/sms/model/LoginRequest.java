package com.rajblowplast.digital.sms.model;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;

    //need default constructor for JSON Parsing
    public LoginRequest()
    {}

    public LoginRequest(String username) {
        this.setUsername(username);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
