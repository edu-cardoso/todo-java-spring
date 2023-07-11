package com.example.todo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskDto(@NotBlank String name, @NotNull Boolean finished, @NotNull Long userId) {
}
