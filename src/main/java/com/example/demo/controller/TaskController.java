package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Task;
import com.example.demo.dto.TaskRequestDTO;
import com.example.demo.dto.TaskUpdateDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AuthService;
import com.example.demo.service.TaskService;

import jakarta.validation.Valid;



@RestController

@RequestMapping("/tasks")
public class TaskController {
  private final TaskService service;
  private final AuthService authService;

  public TaskController(TaskService service, AuthService authService) {
    this.service = service;
    this.authService = authService;
  }

  @GetMapping
  public ApiResponse<List<Task>> getTasks() {
    List<Task> tasks = service.getTasks();
    return new ApiResponse<>(true, tasks, null);
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResponse<Object>> createTask(
    @RequestBody @Valid TaskRequestDTO body, 
    @RequestHeader(value = "x-admin-password", required=false) String password) {
    if(password == null || !authService.isAuthorized(password)){
      return ResponseEntity
        .status(401)
        .body( new ApiResponse<>(false, null, "Senha inválida"));
    }
    Object created = service.createTask(body);
    return ResponseEntity
      .status(201)
      .body(new ApiResponse<>(true, created, null));
  }

  @PatchMapping("/{id}")
  public ResponseEntity< ApiResponse<Object>> updateTask(
    @PathVariable String id, @RequestBody TaskUpdateDTO body,
    @RequestHeader(value="x-admin-password", required=false) String password) {
    if(password == null || !authService.isAuthorized(password)){
      return ResponseEntity
        .status(401)
        .body(new ApiResponse<>(false, null, "Senha inválida"));    
    }
    Object updated = service.updateTask(id, body);
    return ResponseEntity
      .status(200)
      .body( new ApiResponse<>(true, updated, null));

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<String>> deleteTask(
    @PathVariable String id,
    @RequestHeader(value = "x-admin-password", required = false) String password
  ) {
    if (password == null || !authService.isAuthorized(password)) {
      return ResponseEntity
        .status(401)
        .body(new ApiResponse<>(false, null, "Senha inválida"));
    }
      service.deleteTask(id);

    return ResponseEntity
      .ok(new ApiResponse<>(true, "Deletado com sucesso", null));
  }
}