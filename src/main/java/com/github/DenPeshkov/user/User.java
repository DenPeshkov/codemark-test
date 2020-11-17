package com.github.DenPeshkov.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
  @Id private String login;

  private String name;
  private String password;

  public User() {}

  public User(String login, String name, String password) {
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
    return "User{"
        + "login='"
        + login
        + '\''
        + ", name='"
        + name
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }
}
