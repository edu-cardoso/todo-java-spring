package com.example.todo.controllers;

import com.example.todo.dtos.TaskDto;
import com.example.todo.entities.Task;
import com.example.todo.entities.User;
import com.example.todo.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private TaskService service;

  @PostMapping
  public ResponseEntity<Object> createTask(@RequestBody @Valid TaskDto taskDto) {
    var task = new Task();
    BeanUtils.copyProperties(taskDto, task);

    return ResponseEntity.status(HttpStatus.CREATED).body(service.createTask(task));
  }

  @GetMapping
  public ResponseEntity<List<Task>> getAllTasks() {
    return ResponseEntity.status(HttpStatus.OK).body(service.getAllTasks());
  }
}
