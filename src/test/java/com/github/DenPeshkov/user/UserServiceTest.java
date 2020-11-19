package com.github.DenPeshkov.user;

import com.github.DenPeshkov.role.Role;
import com.github.DenPeshkov.role.RoleService;
import com.github.DenPeshkov.role.dto.RoleDto;
import com.github.DenPeshkov.user.dto.UserDtoWithRoles;
import com.github.DenPeshkov.user.exception.UserAlreadyExistsException;
import com.github.DenPeshkov.user.exception.UserDoesntExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock UserRepository userRepository;
  @Mock RoleService roleService;

  @Captor ArgumentCaptor<User> userCaptor;

  @InjectMocks @Spy UserService userService;

  @Test
  void addUser() {
    Role role1 = new Role("role1");
    role1.setId(0L);
    Role role2 = new Role("role2");
    role2.setId(34L);
    Role role3 = new Role("role3");
    role3.setId(5L);

    Set<Role> roles = Set.of(role1, role2, role3);

    UserDtoWithRoles userDto =
        new UserDtoWithRoles(
            "login1",
            "name1",
            "password1",
            Set.of(
                new RoleDto(role1.getName()),
                new RoleDto(role2.getName()),
                new RoleDto(role3.getName())));

    when(userRepository.existsById(any())).thenReturn(false);
    when(roleService.getRoles(anyCollection())).thenReturn(roles);

    userService.addUser(userDto);

    verify(userRepository, times(1)).save(userCaptor.capture());

    User user = userCaptor.getValue();

    Assertions.assertEquals(userDto.getLogin(), user.getLogin());
    Assertions.assertEquals(userDto.getName(), user.getName());
    Assertions.assertEquals(userDto.getPassword(), user.getPassword());
    Assertions.assertEquals(roles, user.getRoles());
  }

  @Test
  void addUserWhenUserAlreadyExists() {
    when(userRepository.existsById(any())).thenReturn(true);

    UserDtoWithRoles userDto =
        new UserDtoWithRoles(
            "login1",
            "name1",
            "password1",
            Set.of(new RoleDto("role1"), new RoleDto("role2"), new RoleDto("role3")));

    UserAlreadyExistsException exception =
        Assertions.assertThrows(
            UserAlreadyExistsException.class, () -> userService.addUser(userDto));
    assertEquals(exception.getLocalizedMessage(), "User with given login: login1 already exists!");
  }

  @Test
  void deleteUserWhenUserDoesntExist() {
    when(userRepository.existsById(any())).thenReturn(false);

    String login = "login1";

    UserDoesntExistException exception =
        Assertions.assertThrows(
            UserDoesntExistException.class, () -> userService.deleteUser(login));
    assertEquals(exception.getLocalizedMessage(), "User with given login: login1 is not found!");
  }

  @Test
  void updateUser() {
    Role role1 = new Role("role1");
    role1.setId(0L);
    Role role2 = new Role("role2");
    role2.setId(34L);
    Role role3 = new Role("role3");
    role3.setId(5L);

    Set<Role> roles = Set.of(role1, role2, role3);

    Set<RoleDto> newRolesDto = Set.of(new RoleDto("role3"), new RoleDto("role57"));

    Role role57 = new Role("role3");
    role3.setId(48L);

    Set<Role> newRoles = Set.of(role3, role57);

    UserDtoWithRoles userDto =
        new UserDtoWithRoles("login1", "new_name", "new_password", newRolesDto);

    User user = new User(userDto.getLogin(), "name1", "password1");
    user.addRoles(roles);

    when(userRepository.existsById(any())).thenReturn(true);
    when(userRepository.findById(userDto.getLogin())).thenReturn(Optional.of(user));
    when(roleService.getRoles(newRolesDto)).thenReturn(newRoles);

    userService.updateUser(userDto);

    verify(userRepository, times(1)).save(userCaptor.capture());

    User updatedUser = userCaptor.getValue();

    Assertions.assertEquals(userDto.getLogin(), updatedUser.getLogin());
    Assertions.assertEquals(userDto.getName(), updatedUser.getName());
    Assertions.assertEquals(userDto.getPassword(), updatedUser.getPassword());
    Assertions.assertEquals(newRoles, updatedUser.getRoles());
  }

  @Test
  void updateUserWhenAlredyExists() {
    UserDtoWithRoles userDto =
        new UserDtoWithRoles(
            "login1",
            "name1",
            "password1",
            Set.of(new RoleDto("role1"), new RoleDto("role2"), new RoleDto("role3")));

    when(userRepository.existsById(any())).thenReturn(false);
    doNothing().when(userService).addUser(any());

    userService.updateUser(userDto);

    verify(userService, times(1)).addUser(userDto);
  }
}
