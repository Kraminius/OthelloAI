package com.othelloai.aiapi.controller;

public interface Callback {
    void onSuccess();
    void onError(Exception e);
}