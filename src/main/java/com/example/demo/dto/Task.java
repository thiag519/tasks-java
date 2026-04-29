package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;


public class Task {
  private String id;
  @NotBlank
  
  private String title;
  private String description;
  private TaskStatus status;
  private LocalDateTime createdAt;

  public String getId() {
    return id;
  };
  public void setId(String id) {
    this.id = id;
  };

  public String getTitle() {
    return title;
  };
  public void setTitle(String title) {
    this.title = title;
  };

  public String getDescription() {
    return description;
  };
  public void setDescription(String description) {
    this.description = description;
  };

  public TaskStatus getStatus() {
    return status;
  };
  public void setStatus(TaskStatus status) {
    this.status = status;
  };

  public LocalDateTime getCreatedAt() {
    return createdAt;
  };
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  };
}
