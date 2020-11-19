package com.github.DenPeshkov.user.dto;

import com.github.DenPeshkov.role.dto.RoleDto;

import java.util.Set;

public class UserDtoWithRoles extends UserDtoWithoutRoles {
  private Set<RoleDto> roles;

  public UserDtoWithRoles(String login, String name, Set<RoleDto> roles) {
    super(login, name);
    this.roles = roles;
  }

  public Set<RoleDto> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleDto> roles) {
    this.roles = roles;
  }
}
