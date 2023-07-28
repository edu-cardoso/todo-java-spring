package com.example.todo.services.exceptions;

public class InvalidAuthException extends RuntimeException {
  public InvalidAuthException(String message) {
    super(message);
  }
}
