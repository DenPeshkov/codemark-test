package com.github.DenPeshkov.user.dto;

public class UserDtoWithoutRoles {
  private String login;
  private String name;

  public UserDtoWithoutRoles(String login, String name) {
    this.login = login;
    this.name = name;
  }

  public String getLogin() {
    return login;
  }

  public String getName() {
    return name;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setName(String name) {
    this.name = name;
  }
}
