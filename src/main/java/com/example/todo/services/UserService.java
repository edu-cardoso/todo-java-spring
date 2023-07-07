package com.example.todo.services;

import com.example.todo.models.UserModel;
import com.example.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public UserModel createUser(UserModel user) {
    return repository.save(user);
  }

}
