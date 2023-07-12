package com.example.todo.services;

import com.example.todo.entities.Task;
import com.example.todo.repositories.TaskRepository;
import com.example.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class TaskService {

  @Autowired
  private TaskRepository repository;
  private UserRepository userRepository;

  public Task createTask(Task task) {
    return repository.save(task);
  }

  public List<Task> getAllTasks() {
    return repository.findAll();
  }

  public Object getTasksByUser(@PathVariable Long id) {
    var tasks = repository.findAllByUserId(id);
    System.out.println(tasks);

    return tasks;
  }
}
