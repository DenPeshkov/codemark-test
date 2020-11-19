package com.github.DenPeshkov.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTestIT {
  @MockBean private UserService userService;

  @Autowired private MockMvc mockMvc;

  // used to emulate browser send JSON requests
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @ValueSource(
      strings = {
        "{\"password\": \"aaa7D\",\"login\": \"login1\",\"name\": \"name1\",\"roles\": [\"role1\",\"role2\"]}"
      })
  @ParameterizedTest
  void addUser(String request) throws Exception {
    mockMvc
        .perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isCreated())
        .andExpect(
            result -> assertEquals(result.getResponse().getContentAsString(), "{success: true}"))
        .andDo(print());
  }

  @ValueSource(
      strings = {
        "{\"password\": \"\",\"login\": \"\",\"name\": \"\",\"roles\": [\"\"]}",
        "{\"password\": \"aa\",\"login\": \"\",\"name\": \"\",\"roles\": [\"\"]}",
        "{\"password\": \"aa8\",\"login\": \"\",\"name\": \"\",\"roles\": [\"\"]}",
        "{\"password\": \"aa8bbDe\",\"login\": \"\",\"name\": \"\",\"roles\": [\"\"]}",
        "{\"password\": \"64646\",\"login\": \"\",\"name\": \"\",\"roles\": [\"\"]}",
        "{\"password\": \"DDD\",\"login\": \"\",\"name\": \"\",\"roles\": [\"\"]}",
        "{\"password\": \"aa8bbDe\",\"login\": \"login1\",\"name\": \"\",\"roles\": [\"\"]}",
      })
  @ParameterizedTest
  void addUserValidationError(String request) throws Exception {

    mockMvc
        .perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof MethodArgumentNotValidException))
        .andDo(print());
  }

  @Test
  void updateUser() {}
}
