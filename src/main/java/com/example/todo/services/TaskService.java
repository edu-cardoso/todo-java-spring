package com.example.todo.services;

import com.example.todo.entities.Task;
import com.example.todo.repositories.TaskRepository;
import com.example.todo.services.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

  @Autowired
  private TaskRepository repository;

  public Task createTask(Task task) {
    return repository.save(task);
  }

  public List<Task> getAllTasks() {
    return repository.findAll();
  }

  public Object getTasksByUser(Long id) {
    return repository.findAllByUserId(id);
  }

  public Task updateTask(Long id, Task task) {
    try {
      var updatedTask = repository.getReferenceById(id);
      if (task.getName() != null) {
        updatedTask.setName(task.getName());
      }
      if (task.getFinished() != null) {
        updatedTask.setFinished(task.getFinished());
      }
      return repository.save(updatedTask);
    } catch (EntityNotFoundException e) {
      throw new NotFoundException("Task not found");
    }
  }

  @DeleteMapping("/{id}")
  public void deleteTask(Long id) {
    if (!repository.existsById(id)) {
      throw new NotFoundException("Task not found");
    }
    repository.deleteById(id);
  }
}
