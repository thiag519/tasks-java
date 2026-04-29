package com.example.demo.response;

public record ApiResponse<T>(boolean success, T data, String erro) {}
