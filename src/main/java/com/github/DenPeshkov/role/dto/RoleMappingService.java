package com.github.DenPeshkov.role.dto;

import com.github.DenPeshkov.role.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleMappingService {
  public RoleDto toRoleDto(Role role) {
    return new RoleDto(role.getName());
  }
}
