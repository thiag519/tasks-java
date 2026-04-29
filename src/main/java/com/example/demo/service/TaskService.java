package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.client.ApiGatewayClient;
import com.example.demo.dto.Task;
import com.example.demo.dto.TaskRequestDTO;
import com.example.demo.dto.TaskUpdateDTO;


@Service
public class TaskService {
  private final ApiGatewayClient client;

  public TaskService(ApiGatewayClient client) {
    this.client = client;
  }

  public List<Task> getTasks() {
    return client.getTasks();
  }

  public Object createTask(TaskRequestDTO dto) { 
    return client.createTask(dto);

  }

  public Object updateTask(String id, TaskUpdateDTO dto) {
    if (dto.getStatus() == null || dto.getStatus().isBlank()) {
        dto.setStatus("todo");
    }
    return client.updateTask(id, dto);
  }

  public void deleteTask(String id) {
    client.deleteTask(id);
  }

}
