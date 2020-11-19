package com.github.DenPeshkov.user.exception;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {
  public UserAlreadyExistsException() {
    super("User with given login already exists!");
  }

  public UserAlreadyExistsException(String login) {
    super(String.format("User with given login: %s already exists!", login));
  }

  public UserAlreadyExistsException(String login, Throwable cause) {
    super(String.format("User with given login: %s already exists!", login), cause);
  }

  public UserAlreadyExistsException(Throwable cause) {
    super(cause);
  }
}
