package com.vvs.springreactiveh2.router;

import com.vvs.springreactiveh2.handler.TodoHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class TodoRouter {

  @Bean
  public RouterFunction<ServerResponse> todoRouterFunction(TodoHandler todoHandler) {
    return RouterFunctions.route()
      .nest(path("/api/todos"), builder -> builder
        .GET("", todoHandler::getAll)
        .GET("/{id}", todoHandler::getTodo)
        .POST("", todoHandler::addTodo)
        .DELETE("/{id}", todoHandler::removeTodo))
      .build();
  }
}
