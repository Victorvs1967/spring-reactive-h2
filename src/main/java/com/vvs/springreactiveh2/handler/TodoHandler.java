package com.vvs.springreactiveh2.handler;

import com.vvs.springreactiveh2.model.Todo;
import com.vvs.springreactiveh2.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class TodoHandler {
  
  @Autowired
  private TodoRepository todoRepository;

  public Mono<ServerResponse> getAll(ServerRequest request) {
    Flux<Todo> todos = todoRepository.findAll();
    return ServerResponse
      .ok()
      .contentType(APPLICATION_JSON)
      .body(todos, Todo.class);
  }

  public Mono<ServerResponse> getTodo(ServerRequest request) {
    return findById(request.pathVariable("id"));
  }

  public Mono<ServerResponse> addTodo(ServerRequest request) {
    Mono<Todo> todo = request.bodyToMono(Todo.class); 
    return ServerResponse
      .ok()
      .contentType(APPLICATION_JSON)
      .body(BodyInserters.fromPublisher(todo.flatMap(this::save), Todo.class));
  }

  public Mono<ServerResponse> removeTodo(ServerRequest request) {
    Mono<Todo> todo = todoRepository.findById(Long.parseLong(request.pathVariable("id")));
    return ServerResponse
      .ok()
      .contentType(APPLICATION_JSON)
      .body(BodyInserters.fromPublisher(todo.flatMap(this::delete), Todo.class));
  }

  private Mono<ServerResponse> findById(String id) {
    return todoRepository.findById((Long.parseLong(id)))
      .flatMap(t -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(t)))
      .switchIfEmpty(ServerResponse.notFound().build());
  }

  private Mono<Todo> save(Todo todo) {
    return Mono.fromSupplier(() -> {
      todoRepository.save(todo).subscribe();
      return todo;
    });
  }

  private Mono<Todo> delete(Todo todo) {
    return Mono.fromSupplier(() -> {
      todoRepository.delete(todo).subscribe();
      return todo;
    });
  }

}
