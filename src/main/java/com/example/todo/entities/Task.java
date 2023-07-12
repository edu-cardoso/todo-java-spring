package com.example.todo.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="tasks")
public class Task implements Serializable {
  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String name;
  private Boolean finished;
  private Long userId;

  public Task() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getFinished() {
    return finished;
  }

  public void setFinished(Boolean finished) {
    this.finished = finished;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Task{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", finished=" + finished +
            ", userId=" + userId +
            '}';
  }
}
