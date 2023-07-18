package com.example.todo.services;

import com.example.todo.dtos.UserResponseDto;
import com.example.todo.entities.User;
import com.example.todo.repositories.UserRepository;
import com.example.todo.services.exceptions.AlreadyExistsException;
import com.example.todo.services.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public List<UserResponseDto> getAllUsers() {
    var result = repository.findAll();

    return result.stream().map(UserResponseDto::new).toList();
  }

  public UserResponseDto getOneUser(Long id) {
    Optional<User> user = repository.findById(id);
    user.orElseThrow(() -> new NotFoundException("User not found"));
    return new UserResponseDto(user.get());
  }

  public User getUserByUsername(String username) {
    return repository.findByUsername(username);
  }

  public UserDetails getUserByEmail(String email) {
    return repository.findByEmail(email);
  }

  public UserResponseDto updateUser(Long id, User user) {
    try {
      var result = repository.findById(id);
      var updatedUser = result.get();

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
      return new UserResponseDto(updatedUser);
    } catch (EntityNotFoundException e) {
        throw new NotFoundException("User not found");
    }
  }

  public void deleteUser(Long id) {
    if (!repository.existsById(id)) {
      throw new NotFoundException("User not found");
    }
    repository.deleteById(id);
  }
}
