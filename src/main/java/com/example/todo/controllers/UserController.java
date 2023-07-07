package com.example.todo.controllers;

import com.example.todo.dtos.UserDto;
import com.example.todo.services.UserService;
import com.example.todo.models.UserModel;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService service;

  @PostMapping("/users")
  public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserDto userDto) {
    var user = new UserModel();
    BeanUtils.copyProperties(userDto, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
  }
}
