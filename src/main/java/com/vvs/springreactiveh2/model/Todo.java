package com.vvs.springreactiveh2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("todo")
public class Todo {
  
  @Id
  private Long id;
  private String text;
  private boolean completed;

}
