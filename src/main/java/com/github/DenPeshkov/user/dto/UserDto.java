package com.github.DenPeshkov.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// TODO factory method
public class UserDto {
  @NotBlank(message = "login is mandatory!")
  private String login;

  @NotBlank(message = "name is mandatory!")
  private String name;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotBlank(message = "password is mandatory!")
  @Pattern(
      regexp = "(^(?=.*\\d)(?=.*[A-Z]).+$)",
      message = "password should contain at least 1 uppercase letter and at least one digit!")
  private String password;

  public UserDto(String login, String name, String password) {
    this.login = login;
    this.name = name;
    this.password = password;
  }

  public String getLogin() {
    return login;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserDto{" + "login='" + login + '\'' + ", name='" + name + '\'' + '}';
  }
}
