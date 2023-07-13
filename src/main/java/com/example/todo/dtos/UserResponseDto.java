package com.example.todo.dtos;

import com.example.todo.entities.User;

public class UserResponseDto {
  private Long id;
  private String username;
  private String email;

  public UserResponseDto() {
  }

  public UserResponseDto(User user) {
    id = user.getId();
    username = user.getUsername();
    email = user.getEmail();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "UserResponseDto{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
