package com.tssi.pueblo_pelicula.error;

import com.tssi.pueblo_pelicula.dto.ErrorDto;
import com.tssi.pueblo_pelicula.error.exception.BusinessException;
import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorDto> handleBusiness(BusinessException e) {
    ErrorDto errorDto = new ErrorDto(e);

    return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    FieldError fieldError = e.getFieldError();
    Validate.notNull(fieldError, "Failed to get the fieldError.");

    String defaultMessage = fieldError.getDefaultMessage();

    ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, defaultMessage, e.getClass().getName());

    return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDto> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
    ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, e.getMessage(), e.getClass().getName());

    return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorDto> MethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
    ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, e.getMessage(), e.getClass().getName());

    return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleUnknown(Exception e) {
    ErrorDto errorDto = new ErrorDto(e);

    return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
  }
}
