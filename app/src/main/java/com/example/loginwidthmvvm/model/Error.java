package com.example.loginwidthmvvm.model;

public class Error {
    private ErrorType errorType;
    private String message;

    public Error(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // định nghĩa các biến, thông báo lỗi
    public enum ErrorType{
        username,
        password,
        phone
    }
}
