package com.vvs.springreactiveh2.router;

import com.vvs.springreactiveh2.handler.TodoHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class TodoRouter {

  @Bean
  public RouterFunction<ServerResponse> todoRouterFunction(TodoHandler todoHandler) {
    return RouterFunctions
      .route(GET("/api/todo").and(accept(APPLICATION_JSON)), todoHandler::getAll)
      .andRoute(GET("/api/todo/{id}").and(accept(APPLICATION_JSON)), todoHandler::getTodo)
      .andRoute(POST("/api/todo").and(accept(APPLICATION_JSON)), todoHandler::addTodo)
      .andRoute(DELETE("/api/todo/{id}").and(accept(APPLICATION_JSON)), todoHandler::removeTodo);
  }
}
