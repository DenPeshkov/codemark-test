package com.github.DenPeshkov.user.exception;

import javax.persistence.EntityNotFoundException;

public class UserDoesntExistException extends EntityNotFoundException {
  public UserDoesntExistException() {
    super("User not found!");
  }

  public UserDoesntExistException(String login) {
    super(String.format("User with given login: %s is not found!", login));
  }
}
