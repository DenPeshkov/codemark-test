package com.github.DenPeshkov.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }
}
