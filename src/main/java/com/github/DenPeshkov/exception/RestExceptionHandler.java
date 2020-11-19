package com.github.DenPeshkov.exception;

import com.github.DenPeshkov.user.exception.UserDoesntExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(UserDoesntExistException.class)
  protected ResponseEntity<Object> handleAccessDeniedException(UserDoesntExistException exception) {
    RestExceptionResponse response =
        new RestExceptionResponse(
            HttpStatus.NOT_FOUND, "User you're a looking for doesn't exists!", exception);

    return handleExceptionInternal(exception, response, null, response.getStatus(), null);
  }
}
