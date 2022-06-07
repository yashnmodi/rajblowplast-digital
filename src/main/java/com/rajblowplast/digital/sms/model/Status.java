package com.rajblowplast.digital.sms.model;

public class Status {
    private String replyCode;
    private String error;
    private String reason;

    public Status() {
    }

    public Status(String replyCode, String error, String reason) {
        this.replyCode = replyCode;
        this.error = error;
        this.reason = reason;
    }

    public String getReplyCode() {
        return replyCode;
    }

    public void setReplyCode(String replyCode) {
        this.replyCode = replyCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
