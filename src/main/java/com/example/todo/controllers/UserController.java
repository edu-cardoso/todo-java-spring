package com.example.todo.controllers;

import com.example.todo.dtos.UserResponseDto;
import com.example.todo.repositories.UserRepository;
import com.example.todo.services.UserService;
import com.example.todo.entities.User;
import com.example.todo.services.exceptions.AlreadyExistsException;
import com.example.todo.services.exceptions.NotFoundException;
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

  @Autowired
  private UserRepository repository;

  @GetMapping
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    var users = service.getAllUsers();

    return ResponseEntity.status(HttpStatus.OK).body(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getOneUser(@PathVariable Long id) {
      var user = service.getOneUser(id);
      return ResponseEntity.ok().body(user);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
    var userByUsername = service.getUserByUsername(user.getUsername());
    var userByEmail = service.getUserByEmail(user.getEmail());

    if (userByUsername != null | userByEmail != null) {
      throw new AlreadyExistsException("Email or username already used");
    }

    var updatedUser = service.updateUser(id, user);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    service.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
