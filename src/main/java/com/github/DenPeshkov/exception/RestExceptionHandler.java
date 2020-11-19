package com.github.DenPeshkov.exception;

import com.github.DenPeshkov.user.exception.IncorrectPutRequestBody;
import com.github.DenPeshkov.user.exception.UserAlreadyExistsException;
import com.github.DenPeshkov.user.exception.UserDoesntExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(UserDoesntExistException.class)
  protected ResponseEntity<Object> handleAccessDeniedException(UserDoesntExistException exception) {
    RestExceptionResponse response =
        new RestExceptionResponse(
            HttpStatus.NOT_FOUND, "User you're a looking for doesn't exist!", exception);

    return handleExceptionInternal(exception, response, null, response.getStatus(), null);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  protected ResponseEntity<Object> handleUserAlreadyExistsException(
      UserAlreadyExistsException exception) {
    RestExceptionResponse response =
        new RestExceptionResponse(
            HttpStatus.CONFLICT, "User you're a looking for already exists!", exception);

    return handleExceptionInternal(exception, response, null, response.getStatus(), null);
  }

  @ExceptionHandler(IncorrectPutRequestBody.class)
  protected ResponseEntity<Object> handleIncorrectPutRequestBodyException(
      IncorrectPutRequestBody exception) {
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.CONFLICT, "Incorrect PUT request body!", exception);

    return handleExceptionInternal(exception, response, null, response.getStatus(), null);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    RestExceptionResponse exceptionResponse =
        new RestExceptionResponse(status, "Incorrect HTTP message", exception);

    return handleExceptionInternal(exception, exceptionResponse, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    class BindingExceptionResponse {
      private final boolean success = false;
      private final Map<String, String> errors;

      public boolean isSuccess() {
        return success;
      }

      public Map<String, String> getErrors() {
        return errors;
      }

      public BindingExceptionResponse(Map<String, String> errors) {
        this.errors = errors;
      }
    }

    BindingExceptionResponse exceptionResponse = new BindingExceptionResponse(errors);

    return handleExceptionInternal(exception, exceptionResponse, headers, status, request);
  }
}
