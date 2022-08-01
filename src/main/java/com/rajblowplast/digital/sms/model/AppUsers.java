package com.rajblowplast.digital.sms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Arrays;

@JsonIgnoreProperties
@Document("MASTER_USERS")
public class AppUsers {

    @Id
    private String id;
    private String username;
    private String password;
    private String mobileNo;
    private String email;
    private String role;
    private boolean loggedIn;
    private String lastLoginDate;
    private boolean locked;
    private String registrationDate;

    public AppUsers() {
    }

    public AppUsers(String username, String password, String mobileNo, String role, boolean locked) {
        this.username = username;
        this.password = password;
        this.mobileNo = mobileNo;
        this.role = role;
        this.locked = locked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "AppUsers{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", locked=" + locked +
                ", registrationDate='" + registrationDate + '\'' +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                '}';
    }
}
