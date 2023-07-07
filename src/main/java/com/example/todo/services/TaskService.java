package com.example.todo.services;

import com.example.todo.entities.Task;
import com.example.todo.entities.User;
import com.example.todo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  private TaskRepository repository;

  public Task createTask(Task task) {
    return repository.save(task);
  }
}
