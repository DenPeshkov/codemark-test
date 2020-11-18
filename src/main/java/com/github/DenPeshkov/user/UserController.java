package com.github.DenPeshkov.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  Iterable<User> getAllUsers() {
    Iterable<User> allUsers = userService.getAllUsers();
    return userService.getAllUsers();
  }
}
