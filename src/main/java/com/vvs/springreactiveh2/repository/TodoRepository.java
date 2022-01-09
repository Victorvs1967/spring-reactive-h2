package com.vvs.springreactiveh2.repository;

import com.vvs.springreactiveh2.model.Todo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {
  
}
