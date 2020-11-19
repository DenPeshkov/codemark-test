package com.github.DenPeshkov.role.dto;

import com.github.DenPeshkov.role.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleMappingServiceTest {
  RoleMappingService roleMappingService = new RoleMappingService();

  @Test
  void toRoleDto() {
    Role role = new Role("role1");
    role.setId(1L);

    RoleDto roleDto = roleMappingService.toRoleDto(role);

    Assertions.assertEquals(role.getName(), roleDto.getName());
  }
}
