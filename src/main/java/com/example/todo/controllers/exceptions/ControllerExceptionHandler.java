package com.example.todo.controllers.exceptions;

import com.example.todo.services.exceptions.AlreadyExistsException;
import com.example.todo.services.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<StandardError> entityNotFound(NotFoundException e, HttpServletRequest request) {
    var err = new StandardError();
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setMessage(e.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<StandardError> entityAlreadyExists(AlreadyExistsException e, HttpServletRequest request) {
    var err = new StandardError();
    err.setStatus(HttpStatus.CONFLICT.value());
    err.setMessage(e.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> argumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
    var err = new StandardError();
    err.setStatus(HttpStatus.CONFLICT.value());
    err.setMessage(e.getDetailMessageArguments()[1].toString().replaceAll("\\[|\\]", ""));

    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
  }
}
