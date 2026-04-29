package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;


public class TaskRequestDTO {
  @NotBlank(message = "O titulo é obrigatorio")
  private String title;
  private String description;
  private String password;

  public String getPassword() {return password;}
  public void setPassword(String password) {this.password = password;}

  public String getTitle() {return title;}
  public void setTitle(String title) {this.title = title;}

  public String getDescription() {return description;}
  public void setDescription(String description) {this.description = description;}

}
