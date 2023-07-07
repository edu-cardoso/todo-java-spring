package com.example.todo.services;

import com.example.todo.models.UserModel;
import com.example.todo.repositories.UserRepository;
import com.example.todo.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public UserModel createUser(UserModel user) {
    return repository.save(user);
  }

  public List<UserModel> getAllUsers() {
    return repository.findAll();
  }

  public UserModel getOneUser(Long id) {
    Optional<UserModel> user = repository.findById(id);
    if (user.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    return user.get();
  }

  public UserModel getUserByEmail(String email) {
    var user = repository.findByEmail(email);

    return user;
  }
}
