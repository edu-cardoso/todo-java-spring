package com.example.todo.controllers;

import com.example.todo.dtos.TaskDto;
import com.example.todo.entities.Task;
import com.example.todo.services.TaskService;
import com.example.todo.services.UserService;
import com.example.todo.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private TaskService service;

  @Autowired
  private UserService userService;

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

  @GetMapping("/user/{id}")
  public ResponseEntity<Object> getTasksByUser(@PathVariable Long id) {
      userService.getOneUser(id);
      return ResponseEntity.status(HttpStatus.OK).body(service.getTasksByUser(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateTask(@PathVariable Long id, @RequestBody Task task) {
    var updatedTask = service.updateTask(id, task);
    return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    service.deleteTask(id);
    return ResponseEntity.noContent().build();
  }
}
