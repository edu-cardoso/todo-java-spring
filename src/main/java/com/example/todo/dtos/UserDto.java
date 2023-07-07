package com.example.todo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserDto(
        @NotBlank(message = "username is required")
        @Length(min = 7, message = "username must have at least 7 characters")
        String username,
        @NotBlank(message = "email is required") @Email(message = "Invalid email address") String email,
        @NotBlank(message = "password is required")
        @Length(min = 7, message = "password must have at least 7 characters")
        String password ) {
}
