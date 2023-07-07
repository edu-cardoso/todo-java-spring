package com.example.todo.controllers;

import com.example.todo.dtos.UserDto;
import com.example.todo.services.UserService;
import com.example.todo.models.UserModel;
import com.example.todo.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  @Autowired
  private UserService service;

  @PostMapping
  public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto userDto) {
    var user = service.getUserByEmail(userDto.email());

    if (user != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    }

    var userModel = new UserModel();
    BeanUtils.copyProperties(userDto, userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(userModel));
  }

  @GetMapping
  public ResponseEntity<List<UserModel>> getAllUsers() {
    var users = service.getAllUsers();
    return ResponseEntity.status(HttpStatus.OK).body(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getOneUser(@PathVariable Long id) {
    try {
      var user = service.getOneUser(id);
      return ResponseEntity.ok(user);
    } catch (NotFoundException e) {
      var errorMessage = e.getMessage();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
    var result = service.getUserByEmail(user.getEmail());

    if (result != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already used");
    }

    try {
      var updatedUser = service.updateUser(id, user);
      return ResponseEntity.ok(updatedUser);
    } catch (NotFoundException e) {
      var errorMessage = e.getMessage();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
  }
}
