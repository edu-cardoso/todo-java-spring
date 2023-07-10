package com.example.todo.services;

import com.example.todo.entities.User;
import com.example.todo.repositories.UserRepository;
import com.example.todo.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public User createUser(User user) {
    return repository.save(user);
  }

  public List<User> getAllUsers() {
    return repository.findAll();
  }

  public User getOneUser(Long id) {
    Optional<User> user = repository.findById(id);
    if (user.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    return user.get();
  }

  public User getUserByUsername(String username) {
    var user = repository.findByUsername(username);

    return user;
  }

  public User updateUser(Long id, User user) {
    var updatedUser = getOneUser(id);

    if (user.getUsername() != null) {
      updatedUser.setUsername(user.getUsername());
    }
    if (user.getEmail() != null) {
      updatedUser.setEmail(user.getEmail());
    }
    if (user.getPassword() != null) {
      var encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
      updatedUser.setPassword(encryptedPassword);
    }
    repository.save(updatedUser);

    return updatedUser;
  }

  public void deleteUser(Long id) {
    getOneUser(id);
    repository.deleteById(id);
  }
}
