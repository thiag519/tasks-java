package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Value("${app.admin.password}")
  private String adminPassword;

  public boolean isAuthorized(String password) {
    return adminPassword.equals(password);
  }
}
