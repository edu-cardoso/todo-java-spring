package com.example.todo.repositories;

import com.example.todo.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findAllByUserId(Long userId);
}
