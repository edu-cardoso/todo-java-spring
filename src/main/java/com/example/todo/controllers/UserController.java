package com.example.todo.controllers;

import com.example.todo.dtos.UserDto;
import com.example.todo.services.UserService;
import com.example.todo.entities.User;
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
    var userEmail = service.getUserByEmail(userDto.email());
    var userUsername = service.getUserByUsername(userDto.username());

    if (userEmail != null | userUsername != null ) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    }

    var userModel = new User();
    BeanUtils.copyProperties(userDto, userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(userModel));
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
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
  public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
    var result = service.getUserByEmail(user.getEmail());
    var userUsername = service.getUserByUsername(user.getUsername());
    if (result != null | userUsername != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Email or username already used");
    }

    try {
      var updatedUser = service.updateUser(id, user);
      return ResponseEntity.ok(updatedUser);
    } catch (NotFoundException e) {
      var errorMessage = e.getMessage();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
    try {
      service.deleteUser(id);
      return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    } catch (NotFoundException e) {
      var errorMessage = e.getMessage();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
  }
}
