package com.github.DenPeshkov.user.dto;

import com.github.DenPeshkov.role.dto.RoleDto;
import com.github.DenPeshkov.role.dto.RoleMappingService;
import com.github.DenPeshkov.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMappingService {
  private final RoleMappingService roleMappingService;

  @Autowired
  public UserMappingService(RoleMappingService roleMappingService) {
    this.roleMappingService = roleMappingService;
  }

  public UserDto toUserDtoWithoutRoles(User user) {
    return new UserDto(user.getLogin(), user.getName(), user.getPassword());
  }

  public UserDtoWithRoles toUserDto(User user) {
    Set<RoleDto> roleDtos =
        user.getRoles().stream().map(roleMappingService::toRoleDto).collect(Collectors.toSet());

    return new UserDtoWithRoles(user.getLogin(), user.getName(), user.getPassword(), roleDtos);
  }
}
