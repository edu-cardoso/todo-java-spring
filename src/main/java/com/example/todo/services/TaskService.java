package com.example.todo.services;

import com.example.todo.entities.Task;
import com.example.todo.repositories.TaskRepository;
import com.example.todo.services.exceptions.NotFoundException;
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
    var tasks = repository.findAllByUserId(id);
    System.out.println(tasks);

    return tasks;
  }

  public Task updateTask(Long id, Task task) {
    Optional<Task> optionalTask = repository.findById(id);
    if (optionalTask.isEmpty()) {
      return null;
    }

    var updatedTask = optionalTask.get();

    if (task.getName() != null) {
      updatedTask.setName(task.getName());
    }
    if (task.getFinished() != null) {
      updatedTask.setFinished(task.getFinished());
    }

    repository.save(updatedTask);

    return updatedTask;
  }

  @DeleteMapping("/{id}")
  public void deleteTask(Long id) {
    if (!repository.existsById(id)) {
      throw new NotFoundException("Task not found");
    }
    repository.deleteById(id);
  }
}
