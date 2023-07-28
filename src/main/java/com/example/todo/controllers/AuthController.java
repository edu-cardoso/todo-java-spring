package com.example.todo.controllers;

import com.example.todo.dtos.AuthDto;
import com.example.todo.dtos.TokenJwtDto;
import com.example.todo.dtos.UserDto;
import com.example.todo.dtos.UserResponseDto;
import com.example.todo.entities.User;
import com.example.todo.infra.security.TokenService;
import com.example.todo.repositories.UserRepository;
import com.example.todo.services.UserService;
import com.example.todo.services.exceptions.AlreadyExistsException;
import com.example.todo.services.exceptions.InvalidAuthException;
import com.example.todo.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private UserRepository repository;

  @Autowired
  private UserService service;

  @Autowired
  private TokenService tokenService;

  @PostMapping(value = "/login")
  public ResponseEntity login(@RequestBody @Valid AuthDto user) {
    try {
      var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());
      var authentication = manager.authenticate(usernamePassword);
      var token = tokenService.generateToken((User) authentication.getPrincipal());
      return ResponseEntity.ok(new TokenJwtDto(token));
    } catch (Exception e) {
        throw new InvalidAuthException("Invalid user");
    }

  }

  @PostMapping(value = "/register")
  public ResponseEntity register(@RequestBody @Valid UserDto user) {
    var userByUsername = service.getUserByUsername(user.username());
    var userByEmail = repository.findByEmail(user.email());

    if (userByEmail != null | userByUsername != null) {
      throw new AlreadyExistsException("Email or username already used");
    }

    var encryptedPassword = new BCryptPasswordEncoder().encode(user.password());
    User newUser = new User(user.username(), user.email(), encryptedPassword);

    repository.save(newUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(newUser));
  }
}
