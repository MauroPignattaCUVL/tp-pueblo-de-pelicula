package com.tssi.pueblo_pelicula.dto;

import com.tssi.pueblo_pelicula.error.BusinessException;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ErrorDto implements Serializable {

  private HttpStatus error;

  private String causedBy;

  private String message;

  public ErrorDto(HttpStatus statusCode, String message, String causedBy) {
    this.error = statusCode;
    this.message = message;
    this.causedBy = causedBy;
  }

  public ErrorDto(BusinessException e) {
    this(e.getStatus(), e.getMessage(), e.getClass().getName());
  }

  public ErrorDto(Exception e) {
    this(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getClass().getName());
  }

  public String getError() {
    return error.getReasonPhrase();
  }

  public Integer getStatus() {
    return error.value();
  }
}
