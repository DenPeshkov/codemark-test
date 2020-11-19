package com.github.DenPeshkov.user.dto;

import com.github.DenPeshkov.role.Role;
import com.github.DenPeshkov.role.dto.RoleMappingService;
import com.github.DenPeshkov.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class UserMappingServiceTest {
  RoleMappingService roleMappingService = new RoleMappingService();

  UserMappingService userMappingService = new UserMappingService(roleMappingService);

  @Test
  void toUserDtoWithoutRoles() {
    User user = new User("login1", "name1", "password1");

    Role role1 = new Role("role1");
    role1.setId(1L);
    Role role2 = new Role("role2");
    role2.setId(48L);
    Role role3 = new Role("role3");
    role3.setId(578L);

    user.addRoles(Set.of(role1, role2, role3));

    UserDto userDto = userMappingService.toUserDtoWithoutRoles(user);

    Assertions.assertEquals(user.getLogin(), userDto.getLogin());
    Assertions.assertEquals(user.getName(), userDto.getName());
    Assertions.assertEquals(user.getPassword(), userDto.getPassword());
  }

  @Test
  void toUserDto() {
    User user = new User("login1", "name1", "password1");

    Role role1 = new Role("role1");
    role1.setId(1L);
    Role role2 = new Role("role2");
    role2.setId(48L);
    Role role3 = new Role("role3");
    role3.setId(578L);

    user.addRoles(Set.of(role1, role2, role3));

    UserDtoWithRoles userDto = userMappingService.toUserDto(user);

    Assertions.assertEquals(user.getLogin(), userDto.getLogin());
    Assertions.assertEquals(user.getName(), userDto.getName());
    Assertions.assertEquals(user.getPassword(), userDto.getPassword());
    Assertions.assertEquals(
        user.getRoles().stream()
            .map(role -> roleMappingService.toRoleDto(role))
            .collect(Collectors.toSet()),
        userDto.getRoles());
  }
}
