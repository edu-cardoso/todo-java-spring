package com.example.todo.controllers;

import com.example.todo.dtos.UserDto;
import com.example.todo.dtos.UserResponseDto;
import com.example.todo.repositories.UserRepository;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  @Autowired
  private UserService service;

  @Autowired
  private UserRepository repository;

  @GetMapping
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
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
    var userByUsername = service.getUserByUsername(user.getUsername());
    var userByEmail = repository.findByEmail(user.getEmail());

    if (userByUsername != null | userByEmail != null) {
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
