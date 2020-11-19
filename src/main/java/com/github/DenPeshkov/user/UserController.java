package com.github.DenPeshkov.user;

import com.github.DenPeshkov.user.dto.UserDto;
import com.github.DenPeshkov.user.dto.UserDtoWithRoles;
import com.github.DenPeshkov.user.exception.IncorrectPutRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{login}")
  @ResponseStatus(HttpStatus.OK)
  UserDtoWithRoles getUser(@PathVariable String login) {
    return userService.getUser(login);
  }

  @DeleteMapping("/{login}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteUser(@PathVariable String login) {
    userService.deleteUser(login);
  }

  @PostMapping
  public ResponseEntity<String> addUser(@Validated @RequestBody UserDtoWithRoles userDto) {
    userService.addUser(userDto);

    return new ResponseEntity<>("{success: true}", HttpStatus.CREATED);
  }

  @PutMapping("/{login}")
  public ResponseEntity<String> updateUser(
      @Validated @RequestBody UserDtoWithRoles userDto, @PathVariable String login)
      throws Exception {

    // A successful PUT of a given representation would suggest that a subsequent GET on that same
    // target resource will result in an equivalent representation being sent in a 200 (OK)
    // response.
    // Надо включать id и в URL и в тело запроса
    if (!login.equals(userDto.getLogin()))
      throw new IncorrectPutRequestBody(
          "Login in body of requests is not the same as login in path parameter!");

    userService.updateUser(userDto);

    return new ResponseEntity<>("{success: true}", HttpStatus.OK);
  }
}
