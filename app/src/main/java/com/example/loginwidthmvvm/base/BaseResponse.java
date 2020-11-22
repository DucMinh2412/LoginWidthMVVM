package com.example.loginwidthmvvm.base;

public class BaseResponse<T> {
    private T data;

    public BaseResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
