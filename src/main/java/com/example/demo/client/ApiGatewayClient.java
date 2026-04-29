package com.example.demo.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dto.Task;
import com.example.demo.dto.TaskRequestDTO;
import com.example.demo.dto.TaskUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class ApiGatewayClient {
  private final WebClient webClient;
  private final ObjectMapper mapper;

  public ApiGatewayClient(
    WebClient.Builder builder,
    @Value("${api.base-url}") String baseUrl
  ) {
    this.webClient = builder
      .baseUrl(baseUrl)
      .build();
    this.mapper = new ObjectMapper();
  }
  //GET
  public List<Task> getTasks() {
    try{
      return webClient.get()
    .uri("/tasks")
    .retrieve()
    .bodyToFlux(Task.class)
    .collectList()
    .retryWhen(
            reactor.util.retry.Retry
                .fixedDelay(2, java.time.Duration.ofSeconds(1)) // 🔥 retry
        )
    .block();
    } catch (Exception e) {
      System.err.println("Erro ao buscar tasks: " + e.getMessage());
      return java.util.Collections.emptyList();
    }
              
  }
  //POST
  public Object createTask(TaskRequestDTO dto){
    try {
      //String body = mapper.writeValueAsString(dto);
      return webClient.post()
      .uri("/tasks")
      .bodyValue(dto)
      .retrieve()
      .bodyToMono(Object.class)
      .block();
    } catch(org.springframework.web.reactive.function.client.WebClientRequestException |
        org.springframework.web.reactive.function.client.WebClientResponseException e) {
      System.err.println("Erro ao criar task: " + e.getMessage());
      throw new RuntimeException("Erro  ao criar task: " + e.getMessage(), e);
    }
  }
  //PATCH
  public Object updateTask(String id, TaskUpdateDTO dto) {
    try{
      //String body = mapper.writeValueAsString(dto);
      return webClient.patch()
      .uri("/tasks/" + id)
      .bodyValue(dto)
      .retrieve()
      .bodyToMono(Task.class)
      .retryWhen(
        reactor.util.retry.Retry.fixedDelay(2, java.time.Duration.ofSeconds(1))
      )
      .block();
    }catch(org.springframework.web.reactive.function.client.WebClientRequestException |
        org.springframework.web.reactive.function.client.WebClientResponseException e) {
        System.err.println("Erro ao atualizar task: " + e.getMessage());
      throw new RuntimeException("Erro  ao criar task: " + e.getMessage(), e);
    }
  }
  //DELETE
  public void deleteTask(String id) {
     webClient.delete()
    .uri("/tasks/" + id)
    .retrieve()
    .bodyToMono(String.class)
    .block();
  }

}