package com.example.todo.controllers.exceptions;

import com.example.todo.services.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<StandardError> entityNotFound(NotFoundException e, HttpServletRequest request) {
    var err = new StandardError();
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setMessage(e.getMessage());


    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }
}
