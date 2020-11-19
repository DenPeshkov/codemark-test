package com.github.DenPeshkov.user;

import com.github.DenPeshkov.role.Role;
import com.github.DenPeshkov.role.RoleService;
import com.github.DenPeshkov.role.dto.RoleDto;
import com.github.DenPeshkov.user.dto.UserDto;
import com.github.DenPeshkov.user.dto.UserDtoWithRoles;
import com.github.DenPeshkov.user.dto.UserMappingService;
import com.github.DenPeshkov.user.exception.UserAlreadyExistsException;
import com.github.DenPeshkov.user.exception.UserDoesntExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final UserMappingService userMappingService;
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public UserService(
      UserRepository userRepository,
      RoleService roleService,
      UserMappingService userMappingService) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.userMappingService = userMappingService;
  }

  public List<UserDto> getAllUsers() {
    List<User> users = userRepository.findAll();

    return users.stream()
        .map(userMappingService::toUserDtoWithoutRoles)
        .collect(Collectors.toList());
  }

  public UserDtoWithRoles getUser(String login) {
    Optional<User> userOptional = userRepository.findById(login);

    if (userOptional.isEmpty()) throw new UserDoesntExistException(login);

    return userMappingService.toUserDto(userOptional.get());
  }

  public void deleteUser(String login) {
    if (!userRepository.existsById(login)) throw new UserDoesntExistException(login);

    userRepository.deleteById(login);
  }

  public void addUser(UserDtoWithRoles userDto) {
    if (userRepository.existsById(userDto.getLogin()))
      throw new UserAlreadyExistsException(userDto.getLogin());

    Set<RoleDto> rolesDto = userDto.getRoles();
    Set<Role> roles = roleService.getRoles(rolesDto);

    User user = new User(userDto.getLogin(), userDto.getName(), userDto.getPassword());
    user.addRoles(roles);

    userRepository.save(user);
  }

  public void updateUser(UserDtoWithRoles newUserDto) {
    if (!userRepository.existsById(newUserDto.getLogin())) {
      addUser(newUserDto);

      return;
    }

    Optional<User> updatedUserOptional = userRepository.findById(newUserDto.getLogin());

    if (updatedUserOptional.isEmpty()) throw new UserDoesntExistException(newUserDto.getLogin());

    User updatedUser = updatedUserOptional.get();

    if (newUserDto.getRoles() != null) {
      Set<RoleDto> newRolesDto = newUserDto.getRoles();
      Set<Role> newRoles = roleService.getRoles(newRolesDto);
      updatedUser.setRoles(newRoles);
    } else logger.debug("user with no rules to update");

    updatedUser.setLogin(newUserDto.getLogin());
    updatedUser.setName(newUserDto.getName());
    updatedUser.setPassword(newUserDto.getPassword());

    userRepository.save(updatedUser);
  }
}
