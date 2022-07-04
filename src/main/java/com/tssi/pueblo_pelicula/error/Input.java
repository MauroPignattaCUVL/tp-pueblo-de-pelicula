package com.tssi.pueblo_pelicula.error;

import org.springframework.http.HttpStatus;

import java.util.Collection;

public class Input {

  public static void found(final Object o, final String message) {
    if (o == null) {
      throw new BusinessException(message, HttpStatus.NOT_FOUND);
    }
  }

  public static void notNull(final Object o, final String message) {
    if (o == null) {
      throw new BusinessException(message, HttpStatus.BAD_REQUEST);
    }
  }

  public static void isTrue(final boolean expression, final String message) {
    if (!expression) {
      throw new BusinessException(message, HttpStatus.BAD_REQUEST);
    }
  }

  public static void notEmpty(final Collection<?> collection, final String message) {
    if (collection.isEmpty()) {
      throw new BusinessException(message, HttpStatus.BAD_REQUEST);
    }
  }
}
